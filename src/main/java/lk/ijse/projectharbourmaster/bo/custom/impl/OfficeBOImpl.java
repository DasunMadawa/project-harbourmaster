package lk.ijse.projectharbourmaster.bo.custom.impl;

import lk.ijse.projectharbourmaster.bo.custom.OfficeBO;
import lk.ijse.projectharbourmaster.dao.DAOFactory;
import lk.ijse.projectharbourmaster.dao.custom.OfficeDAO;
import lk.ijse.projectharbourmaster.dto.EmployeeDTO;
import lk.ijse.projectharbourmaster.dto.tm.OfficeTM;
import lk.ijse.projectharbourmaster.entity.Employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfficeBOImpl implements OfficeBO {

    OfficeDAO officeDAO = (OfficeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.OFFICE);

    @Override
    public List<EmployeeDTO> getAllEmployees() throws SQLException {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (Employee employee : officeDAO.getAll()) {
            employeeDTOList.add(
                    new EmployeeDTO(
                            employee.getNic(),
                            employee.getPhoto(),
                            employee.getName(),
                            employee.getBod(),
                            employee.getAddress(),
                            employee.getGender(),
                            employee.getSalary(),
                            employee.getPosition(),
                            employee.getEmail(),
                            employee.getContact()
                    )
            );
        }
        return employeeDTOList;

    }

    @Override
    public boolean inseartData(EmployeeDTO employeeDTO) throws SQLException, IOException {
        return officeDAO.add(
                new Employee(
                        employeeDTO.getNic(),
                        employeeDTO.getPhoto(),
                        employeeDTO.getName(),
                        employeeDTO.getDob(),
                        employeeDTO.getAddress(),
                        employeeDTO.getGender(),
                        employeeDTO.getSalary(),
                        employeeDTO.getPosition(),
                        employeeDTO.getEmail(),
                        employeeDTO.getContact()
                )
        );

    }

    @Override
    public EmployeeDTO searchEmployee(String nicSearchTxtText) throws SQLException, IOException {
        Employee employee = officeDAO.search(nicSearchTxtText);

        return new EmployeeDTO(
                employee.getNic(),
                employee.getPhoto(),
                employee.getName(),
                employee.getBod(),
                employee.getAddress(),
                employee.getGender(),
                employee.getSalary(),
                employee.getPosition(),
                employee.getEmail(),
                employee.getContact()
        );

    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO, String nicOld) throws IOException, SQLException {
        return officeDAO.update(
                new Employee(
                        employeeDTO.getNic(),
                        employeeDTO.getPhoto(),
                        employeeDTO.getName(),
                        employeeDTO.getDob(),
                        employeeDTO.getAddress(),
                        employeeDTO.getGender(),
                        employeeDTO.getSalary(),
                        employeeDTO.getPosition(),
                        employeeDTO.getEmail(),
                        employeeDTO.getContact()
                ) ,
                nicOld
        );
    }

    @Override
    public boolean deleteEmployee(String nic) throws SQLException {
        return officeDAO.delete(nic);

    }

}
