package lk.ijse.projectharbourmaster.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    public List<T> getAll() throws SQLException;
    public boolean add(T entity) throws SQLException, IOException;
    public boolean update(T entity , String id) throws SQLException, IOException;
    public T search(String id) throws SQLException, IOException;
    public boolean delete(String id) throws SQLException;

}
