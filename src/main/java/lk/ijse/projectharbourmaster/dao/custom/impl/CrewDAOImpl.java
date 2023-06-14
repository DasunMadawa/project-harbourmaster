package lk.ijse.projectharbourmaster.dao.custom.impl;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import lk.ijse.projectharbourmaster.dao.custom.CrewDAO;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.CrewDTO;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.entity.Crew;
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

public class CrewDAOImpl implements CrewDAO {


    @Override
    public List<Crew> getAll() throws SQLException {
        String sql = "SELECT * FROM crew ";

        ResultSet rs = CrudUtil.execute(sql);

        List<Crew> crewList = new ArrayList<>();

        while (rs.next()){
            String nic = rs.getString(1);
            String name = rs.getString(2);
            String address = rs.getString(5);
            String contact = rs.getString(8);
            String dob = rs.getString(4);

            crewList.add(new Crew(nic , name , address , contact , dob));
        }

        return crewList;
    }

    @Override
    public boolean add(Crew crew) throws SQLException, IOException {
        PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement( "INSERT INTO crew VALUES( ? , ? , ? , ? , ? , ? , ? , ? )" );

        if (crew.getEmail().length() == 0){
            crew.setEmail(null);
        }
        if (crew.getContact().length() == 0){
            crew.setContact(null);
        }

        if (crew.getPhoto() != null){
            BufferedImage bImage = SwingFXUtils.fromFXImage(crew.getPhoto(), null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ImageIO.write(bImage, "png", outputStream);
            byte[] res = outputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(res);

            ps.setBinaryStream(3 , inputStream , inputStream.available());

        }else {
            ps.setBinaryStream(3 , null , 0);
        }

        ps.setString(1 , crew.getNic());
        ps.setString(2 , crew.getName());

        ps.setString(4 , crew.getBod());
        ps.setString(5 , crew.getAddress());
        ps.setString(6 , crew.getGender());
        ps.setString(7 , crew.getEmail());
        ps.setString(8 , crew.getContact());


        return ps.executeUpdate() > 0;

    }

    @Override
    public boolean update(Crew crew, String id) throws SQLException, IOException {
        PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement( "UPDATE crew SET nic = ? , name = ? , photo = ? , bod = ? , address = ? , gender = ? , email = ? , contact = ? WHERE nic = ?" );

        if (crew.getEmail() == null){
            crew.setEmail(null);
        }

        if (crew.getPhoto() != null){
            BufferedImage bImage = SwingFXUtils.fromFXImage(crew.getPhoto(), null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ImageIO.write(bImage, "png", outputStream);
            byte[] res = outputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(res);

            ps.setBinaryStream(3 , inputStream , inputStream.available());

        }else {
            ps.setBinaryStream(3 , null , 0);
        }

        ps.setString(1 , crew.getNic());
        ps.setString(2 , crew.getName());

        ps.setString(4 , crew.getBod());
        ps.setString(5 , crew.getAddress());
        ps.setString(6 , crew.getGender());
        ps.setString(7 , crew.getEmail());
        ps.setString(8 , crew.getContact());
        ps.setString(9 , id);

        return ps.executeUpdate() > 0;

    }

    @Override
    public Crew search(String id) throws SQLException, IOException {
        String sql = "SELECT * FROM crew WHERE nic = ? || name = ?";

        ResultSet rs = CrudUtil.execute(sql, id, id);

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

            return new Crew(nic , name , photo ,dob , address , gender , email , contact );

        }

        return null;

    }

    @Override
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM boat WHERE boatId = ?";

        return CrudUtil.execute(sql, id);

    }

    @Override
    public List<String> getAllEmails() throws SQLException {
        String sql = "SELECT boatOwnerEmail FROM boat WHERE boatOwnerEmail IS NOT NULL;";

        ResultSet rs = CrudUtil.execute(sql);

        List<String> emails = new ArrayList<>();

        while (rs.next()) {
            emails.add(rs.getString(1));
        }

        return emails;

    }

}
