package dat.daos;

import java.util.Collection;

public interface GenericDAO<T, D> {
	Collection<T> findAll();
	T persistEntity(T entity);
	void removeEntity(D id);
	T findEntity(D id);
	void updateEntity(T entity, D id);
}