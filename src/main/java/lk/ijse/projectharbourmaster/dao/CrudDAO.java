package lk.ijse.projectharbourmaster.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    public List<T> getAll() throws SQLException;
    public boolean add(T entity) throws SQLException;
    public boolean update(T entity , String id) throws SQLException;
    public T search(String id) throws SQLException;
    public boolean delete(String id) throws SQLException;

}
