package lk.ijse.projectharbourmaster.model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.Crew;
import lk.ijse.projectharbourmaster.dto.Employee;
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

public class OfficeModel {
    public static List<OfficeTM> getAllEmployees() throws SQLException {
        String sql = "SELECT nic , name , address , contact , TIMESTAMPDIFF(YEAR , bod ,CURDATE()) AS age , gender , position FROM employee";

        ResultSet rs = CrudUtil.execute(sql);

        List<OfficeTM> officeTMList = new ArrayList<>();

        while (rs.next()){
            officeTMList.add(
                    new OfficeTM(
                            rs.getString(1) ,
                            rs.getString(2) ,
                            rs.getString(3) ,
                            rs.getString(4) ,
                            rs.getString(5) ,
                            rs.getString(6) ,
                            rs.getString(7)
                    )
            );
        }

        return officeTMList;

    }

    public static boolean inseartData(Employee employee) throws SQLException, IOException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement ps = con.prepareStatement("INSERT INTO employee VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )");

        if (employee.getEmail().length() == 0){
            employee.setEmail(null);
        }
        if (employee.getContact().length() == 0){
            employee.setContact(null);
        }

        if (employee.getPhoto() != null){
            BufferedImage bImage = SwingFXUtils.fromFXImage(employee.getPhoto(), null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ImageIO.write(bImage, "png", outputStream);
            byte[] res = outputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(res);

            ps.setBinaryStream(2 , inputStream , inputStream.available());

        }else {
            ps.setBinaryStream(2 , null , 0);
        }

        ps.setString( 1 , employee.getNic());
        ps.setString( 3 , employee.getName());
        ps.setString( 4 , employee.getDob());
        ps.setString( 5 , employee.getAddress());
        ps.setString( 6 , employee.getGender());
        ps.setString( 7 , employee.getSalary());
        ps.setString( 8 , employee.getPosition());
        ps.setString( 9 , employee.getEmail());
        ps.setString( 10 , employee.getContact());

        return ps.executeUpdate() > 0;

    }

    public static Employee searchEmployee(String nicSearchTxtText) throws SQLException, IOException {
        String sql = "SELECT * FROM employee WHERE nic = ? || name = ?";

        ResultSet rs = CrudUtil.execute(sql, nicSearchTxtText, nicSearchTxtText);

        if (rs.next()) {
            String nic = rs.getString(1);

            Blob imageBlob = rs.getBlob(2);
            WritableImage photo = null;

            if (imageBlob != null) {
                InputStream input = imageBlob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(input);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);

                photo = image;
            }

            String name = rs.getString(3);
            String dob = rs.getString(4);
            String address = rs.getString(5);
            String gender = rs.getString(6);
            String salary = rs.getString(7);
            String position = rs.getString(8);
            String email = rs.getString(9);
            String contact = rs.getString(10);

            return new Employee(nic, photo, name, dob, address, gender, salary, position, email, contact);
        }
        return null;
    }

    public static boolean updateEmployee(Employee employee, String nicOld) throws IOException, SQLException {
        PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement( "UPDATE employee SET nic = ? , photo = ? , name = ? , bod = ? , address = ? , gender = ? , salary = ? , position = ?, email = ? , contact = ? WHERE nic = ?" );

        if (employee.getEmail() == null){
            employee.setEmail(null);
        }

        if (employee.getPhoto() != null){
            BufferedImage bImage = SwingFXUtils.fromFXImage(employee.getPhoto(), null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ImageIO.write(bImage, "png", outputStream);
            byte[] res = outputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(res);

            ps.setBinaryStream(2 , inputStream , inputStream.available());

        }else {
            ps.setBinaryStream(2 , null , 0);
        }

        ps.setString( 1 , employee.getNic());
        ps.setString( 3 , employee.getName());
        ps.setString( 4 , employee.getDob());
        ps.setString( 5 , employee.getAddress());
        ps.setString( 6 , employee.getGender());
        ps.setString( 7 , employee.getSalary());
        ps.setString( 8 , employee.getPosition());
        ps.setString( 9 , employee.getEmail());
        ps.setString( 10 , employee.getContact());
        ps.setString(11 , nicOld);

        return ps.executeUpdate() > 0;

    }

    public static boolean dropEmployee(String nic) throws SQLException {
        String sql = "DELETE FROM employee WHERE nic = ?";

        return CrudUtil.execute(sql , nic);

    }

}
