package lk.ijse.projectharbourmaster.bo.custom.impl;

import lk.ijse.projectharbourmaster.bo.custom.StockBO;
import lk.ijse.projectharbourmaster.dao.DAOFactory;
import lk.ijse.projectharbourmaster.dao.custom.FishDAO;
import lk.ijse.projectharbourmaster.dao.custom.QueryDAO;
import lk.ijse.projectharbourmaster.dao.custom.StockDAO;
import lk.ijse.projectharbourmaster.dao.custom.StockFishDAO;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.CustomDTO;
import lk.ijse.projectharbourmaster.dto.FishDTO;
import lk.ijse.projectharbourmaster.dto.StockUpdateDTO;
import lk.ijse.projectharbourmaster.entity.CustomEntity;
import lk.ijse.projectharbourmaster.entity.Fish;
import lk.ijse.projectharbourmaster.entity.Stock;
import lk.ijse.projectharbourmaster.entity.Stock_fish;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockBOImpl implements StockBO {
    FishDAO fishDAO = (FishDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.FISH);
    StockDAO stockDAO = (StockDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCK);
    StockFishDAO stockFishDAO = (StockFishDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCK_FISH);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY_DAO);


    @Override
    public List<FishDTO> getAllOrderBy(String order) throws SQLException {
        List<FishDTO> fishDTOList = new ArrayList<>();

        for (Fish fish : fishDAO.getAllOrderBy(order)) {
            fishDTOList.add(
                    new FishDTO(
                            fish.getFishId(),
                            fish.getName(),
                            fish.getUnitPrice(),
                            fish.getStock()
                    )
            );
        }

        return fishDTOList;

    }

    @Override
    public List<CustomDTO> getAllStockRecords() throws SQLException {
        List<CustomDTO> customDTOList = new ArrayList<>();

        for (CustomEntity customEntity : queryDAO.getAllStockFishRecords()) {
            customDTOList.add(
                    new CustomDTO(
                            customEntity.getStockId(),
                            customEntity.getFishId(),
                            customEntity.getWeight(),
                            customEntity.getDate(),
                            customEntity.getUnitPriceBought(),
                            customEntity.getAddOrRemove(),
                            customEntity.getFishName()
                    )
            );
        }

        return customDTOList;

    }

    @Override
    public double getAvailableSpace(String stockId) throws SQLException {
        return stockDAO.getAvailableSpace(stockId);

    }

    @Override
    public FishDTO searchFish(String fishId) throws SQLException, IOException {
        Fish fish = fishDAO.search(fishId);
        if (fish == null){
            return null;
        }
        return new FishDTO(fish.getFishId(), fish.getName(), fish.getUnitPrice(), fish.getStock());

    }

    @Override
    public boolean addStock(StockUpdateDTO stockUpdateDTO) {
        try {
            if (stockUpdateDTO.isAdd()) {
                if (!stockDAO.spaceCheck(stockUpdateDTO.getStockId(), stockUpdateDTO.getWeight())) {
                    System.out.println("No Space Left");
                    return false;
                }
            } else {
                Fish fish = fishDAO.search(stockUpdateDTO.getFishDTO().getFishId());

                FishDTO fishDTO = new FishDTO(fish.getFishId(), fish.getName(), fish.getUnitPrice(), fish.getStock());

                double balance = fishDTO.getStock() - stockUpdateDTO.getWeight();

                if (balance < 0) {
                    System.out.println("No Stock Left On This Fish");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            String addOrRemove = "ADD";
            if (!stockUpdateDTO.isAdd()) {
                addOrRemove = "REMOVE";
            }

            boolean isUpdatedStockFish = stockFishDAO.add(new Stock_fish(stockUpdateDTO.getStockId(), stockUpdateDTO.getFishDTO().getFishId(), stockUpdateDTO.getWeight(), null, stockUpdateDTO.getFishDTO().getUnitPrice(), addOrRemove));
            if (isUpdatedStockFish) {
                //
                double availableSpace = stockDAO.getAvailableSpace(stockUpdateDTO.getStockId());
                double balance = 0;

                if ( stockUpdateDTO.isAdd() ){
                    balance = availableSpace - stockUpdateDTO.getWeight();
                }else {
                    balance = availableSpace + stockUpdateDTO.getWeight();
                };
                //

                boolean isUpdatedStock = stockDAO.stockSpaceUpdate(
                        new Stock(
                                stockUpdateDTO.getStockId(),
                                0,
                                balance
                        )
                );

                if (isUpdatedStock) {
                    //
                    Fish fish = fishDAO.search(stockUpdateDTO.getFishDTO().getFishId());
                    FishDTO fishDTO = new FishDTO(fish.getFishId() , fish.getName() , fish.getUnitPrice() , fish.getStock() );

                    double currentStock = fishDTO.getStock();

                    double newStock = 0;
                    if (stockUpdateDTO.isAdd()){
                        newStock = currentStock + stockUpdateDTO.getWeight();
                    }else {
                        newStock = currentStock - stockUpdateDTO.getWeight();
                    }
                    //

                    boolean isUpdatedFishstock = fishDAO.updateFishStock(newStock , fishDTO.getFishId());
                    if (isUpdatedFishstock) {
                        con.commit();
                        return true;
                    }

                }


            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;

    }

}
