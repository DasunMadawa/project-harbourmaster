package lk.ijse.projectharbourmaster.bo.custom.impl;

import lk.ijse.projectharbourmaster.bo.custom.FishBO;
import lk.ijse.projectharbourmaster.dao.DAOFactory;
import lk.ijse.projectharbourmaster.dao.custom.FishDAO;
import lk.ijse.projectharbourmaster.dto.FishDTO;
import lk.ijse.projectharbourmaster.dto.StockUpdateDTO;
import lk.ijse.projectharbourmaster.entity.Fish;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FishBOImpl implements FishBO {
    FishDAO fishDAO = (FishDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.FISH);

    @Override
    public FishDTO searchFish(String fishId) throws SQLException, IOException {
        Fish fish = fishDAO.search(fishId);
        return new FishDTO(fish.getFishId() , fish.getName() , fish.getUnitPrice(), fish.getStock() );

    }

    @Override
    public List<FishDTO> getAllOrderBy(String order) throws SQLException {
        List<FishDTO> fishDTOList = new ArrayList<>();

        for (Fish fish: fishDAO.getAllOrderBy( order ) ) {
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
    public boolean addFish(FishDTO fishDTO) throws SQLException, IOException {
        return fishDAO.add(new Fish(fishDTO.getFishId(), fishDTO.getFishName(), fishDTO.getUnitPrice(), fishDTO.getStock()));

    }

    @Override
    public boolean updateFishPrice(FishDTO fishDTO , String id) throws SQLException, IOException {
        return fishDAO.update(new Fish(fishDTO.getFishId(), fishDTO.getFishName(), fishDTO.getUnitPrice(), fishDTO.getStock() ) , id );

    }

    @Override
    public boolean deleteFish(String fishId) throws SQLException {
        return fishDAO.delete(fishId);

    }

}
