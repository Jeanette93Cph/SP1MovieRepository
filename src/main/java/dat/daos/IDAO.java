package dat.daos;

import java.util.Set;

public interface IDAO<T, Long> {
    boolean create(T type);
    boolean delete(T type);
    T getById(Long id);
    Set<T> getAll();
    boolean update(T t);
}