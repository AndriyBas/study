package dao;
import java.util.List;
import java.util.UUID;


import configuration.Configuration;
import dao.exception.DAOException;

public interface  CRUDInterface{
	public void open(Configuration configuration) 								throws DAOException;
	public void close()															throws DAOException;
	public <T> 	T 		insert			(T instance)							throws DAOException;
	public <T> 	T 		read			(Class entityClass, UUID id)			throws DAOException;
	public <T> 	void 	update			(T instance)							throws DAOException;
	public <T>	void 	delete			(T instance)							throws DAOException;
	public <T>	List<T> select			(Class entityClass,DAOFilter filter)	throws DAOException;
	public <T>	List<T> select			(String SQLString) 						throws DAOException;
	
}




