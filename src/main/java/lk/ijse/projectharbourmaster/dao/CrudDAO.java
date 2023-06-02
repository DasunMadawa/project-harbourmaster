package lk.ijse.projectharbourmaster.dao;

import java.util.List;

public interface CrudDAO<T> {
    public List<T> getAll();
    public boolean add(T dto);
    public boolean update(T dto);
    public T search(String id);
    public boolean delete(String id);

}
