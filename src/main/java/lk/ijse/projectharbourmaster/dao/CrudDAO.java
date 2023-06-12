package lk.ijse.projectharbourmaster.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T , TM> extends SuperDAO {
    public List<TM> getAll() throws SQLException;
    public boolean add(T dto) throws SQLException;
    public boolean update(T dto , String id) throws SQLException;
    public T search(String id) throws SQLException;
    public boolean delete(String id) throws SQLException;

}
