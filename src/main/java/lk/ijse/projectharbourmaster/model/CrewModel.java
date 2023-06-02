package lk.ijse.projectharbourmaster.model;

import javafx.scene.image.WritableImage;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.CrewDTO;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;

public class CrewModel {
    public static boolean inseartData(CrewDTO crewDTO) throws SQLException, IOException {
        PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement( "INSERT INTO crew VALUES( ? , ? , ? , ? , ? , ? , ? , ? )" );

        if (crewDTO.getEmail().length() == 0){
            crewDTO.setEmail(null);
        }
        if (crewDTO.getContact().length() == 0){
            crewDTO.setContact(null);
        }

        if (crewDTO.getPhoto() != null){
            BufferedImage bImage = SwingFXUtils.fromFXImage(crewDTO.getPhoto(), null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ImageIO.write(bImage, "png", outputStream);
            byte[] res = outputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(res);

            ps.setBinaryStream(3 , inputStream , inputStream.available());

        }else {
            ps.setBinaryStream(3 , null , 0);
        }

        ps.setString(1 , crewDTO.getNic());
        ps.setString(2 , crewDTO.getName());

        ps.setString(4 , crewDTO.getDob());
        ps.setString(5 , crewDTO.getAddress());
        ps.setString(6 , crewDTO.getGender());
        ps.setString(7 , crewDTO.getEmail());
        ps.setString(8 , crewDTO.getContact());


        return ps.executeUpdate() > 0;

    }

    public static List<CrewTM> getAllForTableFilter(String... args) throws SQLException {
        String orderBy = "";
        if (args.length == 1){
            orderBy = args[0];
        }
        String sql = "SELECT * FROM crew "+orderBy;

        ResultSet rs = CrudUtil.execute(sql);

        List<CrewTM> crewTMList = new ArrayList<>();

        while (rs.next()){
            String nic = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(5);
            String contact = rs.getString(8);
            String dob = rs.getString(4);

            int age = LocalDate.now().getYear() - Integer.valueOf(dob.substring(0, 4));

            crewTMList.add(new CrewTM(nic , name , address , contact , age+""));
        }

        return crewTMList;
    }


    public static CrewDTO searchCrew(String nicSearchTxtText) throws SQLException, IOException {
        String sql = "SELECT * FROM crew WHERE nic = ? || name = ?";

        ResultSet rs = CrudUtil.execute(sql, nicSearchTxtText, nicSearchTxtText);

        if (rs.next()){
            String nic = rs.getString(1);
            String name = rs.getString(2);

            Blob imageBlob = rs.getBlob(3);
            WritableImage photo = null;

            if (imageBlob != null) {
                InputStream input = imageBlob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(input);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);

                photo = image;
            }

            String dob = rs.getString(4);
            String address = rs.getString(5);
            String gender = rs.getString(6);
            String email = rs.getString(7);
            String contact = rs.getString(8);

            return new CrewDTO(nic , name , photo ,dob , address , gender , email , contact );

        }

        return null;

    }

    public static boolean updateCrew(CrewDTO crewDTO, String nicOld) throws SQLException, IOException {
        PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement( "UPDATE crew SET nic = ? , name = ? , photo = ? , bod = ? , address = ? , gender = ? , email = ? , contact = ? WHERE nic = ?" );

        if (crewDTO.getEmail() == null){
            crewDTO.setEmail(null);
        }

        if (crewDTO.getPhoto() != null){
            BufferedImage bImage = SwingFXUtils.fromFXImage(crewDTO.getPhoto(), null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ImageIO.write(bImage, "png", outputStream);
            byte[] res = outputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(res);

            ps.setBinaryStream(3 , inputStream , inputStream.available());

        }else {
            ps.setBinaryStream(3 , null , 0);
        }

        ps.setString(1 , crewDTO.getNic());
        ps.setString(2 , crewDTO.getName());

        ps.setString(4 , crewDTO.getDob());
        ps.setString(5 , crewDTO.getAddress());
        ps.setString(6 , crewDTO.getGender());
        ps.setString(7 , crewDTO.getEmail());
        ps.setString(8 , crewDTO.getContact());
        ps.setString(9 , nicOld);

        return ps.executeUpdate() > 0;

    }

    public static boolean dropCrew(String nic) throws SQLException {
        String sql = "DELETE FROM crew WHERE nic = ?";

        return CrudUtil.execute(sql , nic);

    }


    public static List<String> getAllEmails() throws SQLException {
        String sql = "SELECT email FROM crew WHERE email IS NOT NULL;";

        ResultSet rs = CrudUtil.execute(sql);

        List<String> emails = new ArrayList<>();

        while (rs.next()){
            emails.add(rs.getString(1));
        }


        return emails;
    }
}
