package ra.edu.business.dao;

import java.util.List;

public interface AppDAO<T> {
    List<T> findAll();
    List<T> findPerPage(int page);
    boolean save(T t);
    boolean update(T t);
    boolean delete(T t);
}
