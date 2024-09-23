package dat.daos;

public interface IDAO<T> {
	T create(T t);
	T read(Object id);
	T update(T t);
	void delete(Object id);
}