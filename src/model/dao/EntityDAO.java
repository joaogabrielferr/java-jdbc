package model.dao;

import java.util.List;

public interface EntityDAO<T> {

	void insert(T entity);
	void update(T entity);
	void delete(Integer id);
	T findById(Integer id);
	List<T> findAll();
	

}
