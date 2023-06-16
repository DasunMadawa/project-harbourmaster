package lk.ijse.projectharbourmaster.bo.custom;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import lk.ijse.projectharbourmaster.bo.SuperBO;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.EmployeeDTO;
import lk.ijse.projectharbourmaster.dto.tm.OfficeTM;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface OfficeBO extends SuperBO {
    public List<EmployeeDTO> getAllEmployees() throws SQLException;

    public boolean inseartData(EmployeeDTO employeeDTO) throws SQLException, IOException;

    public EmployeeDTO searchEmployee(String nicSearchTxtText) throws SQLException, IOException;

    public boolean updateEmployee(EmployeeDTO employeeDTO, String nicOld) throws IOException, SQLException;

    public boolean deleteEmployee(String nic) throws SQLException;

}
