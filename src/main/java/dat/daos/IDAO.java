package dat.daos;

import dat.entities.Movie;

import java.util.List;

//interface for the DAO classes - CRUD operations
public interface IDAO<T> {
	void create (T t);
	void read(T t);
	void update(T t);
	void delete(T t);
	T findById(Long id);
	List<T> findAll();
}
