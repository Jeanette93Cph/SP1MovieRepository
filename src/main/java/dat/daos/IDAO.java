package dat.daos;

import java.util.List;

public interface IDAO<T> {
	List<T> findAll();
	T persistEntity(T entity);
	void removeEntity(Long id);
	T findEntity(Long id);
	T updateEntity(T entity, Long id);
}