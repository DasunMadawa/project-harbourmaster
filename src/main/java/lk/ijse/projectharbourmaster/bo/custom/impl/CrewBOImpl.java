package lk.ijse.projectharbourmaster.bo.custom.impl;

import lk.ijse.projectharbourmaster.bo.custom.CrewBO;
import lk.ijse.projectharbourmaster.dao.DAOFactory;
import lk.ijse.projectharbourmaster.dao.custom.CrewDAO;
import lk.ijse.projectharbourmaster.dto.CrewDTO;
import lk.ijse.projectharbourmaster.entity.Crew;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CrewBOImpl implements CrewBO {
    CrewDAO crewDAO = (CrewDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CREW);

    @Override
    public boolean addCrew(CrewDTO crewDTO) throws SQLException, IOException {
        return crewDAO.add(new Crew(
                        crewDTO.getNic(),
                        crewDTO.getName(),
                        crewDTO.getPhoto(),
                        crewDTO.getDob(),
                        crewDTO.getAddress(),
                        crewDTO.getGender(),
                        crewDTO.getEmail(),
                        crewDTO.getContact()
                )

        );

    }

    @Override
    public boolean updateCrew(CrewDTO crewDTO, String id) throws SQLException, IOException {
        return crewDAO.update(
                new Crew(
                        crewDTO.getNic(),
                        crewDTO.getName(),
                        crewDTO.getPhoto(),
                        crewDTO.getDob(),
                        crewDTO.getAddress(),
                        crewDTO.getGender(),
                        crewDTO.getEmail(),
                        crewDTO.getContact()
                ),
                id

        );

    }

    @Override
    public CrewDTO searchCrew(String id) throws SQLException, IOException {
        Crew crew = crewDAO.search(id);

        return new CrewDTO(crew.getNic(),
                crew.getName(),
                crew.getPhoto(),
                crew.getBod(),
                crew.getAddress(),
                crew.getGender(),
                crew.getEmail(),
                crew.getContact()
        );

    }

    @Override
    public boolean deleteCrew(String id) throws SQLException, IOException {
        return crewDAO.delete(id);

    }

    @Override
    public List<CrewDTO> getAll() throws SQLException {
        List<CrewDTO> crewDTOList = new ArrayList<>();

        for (Crew crew : crewDAO.getAll()) {
            crewDTOList.add(
                    new CrewDTO(crew.getNic(),
                            crew.getName(),
                            crew.getPhoto(),
                            (LocalDate.now().getYear() - Integer.valueOf(crew.getBod() ))+"" ,
                            crew.getAddress(),
                            crew.getGender(),
                            crew.getEmail(),
                            crew.getContact()
                    )
            );
        }
        return crewDTOList;

    }

    @Override
    public List<String> getAllEmails() throws SQLException {
        return crewDAO.getAllEmails();
    }

}
