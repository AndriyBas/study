package dao;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


import configuration.Configuration;
import dao.exception.DAOException;

public class TransparentCRUD implements CRUDInterface{
	private CRUDInterface nextCRUD = null;
	
	public TransparentCRUD(){}

	public TransparentCRUD(CRUDInterface nextCRUD) {
		super();
		this.nextCRUD = nextCRUD;
	}

	public CRUDInterface getNextCRUD() {
		return nextCRUD;
	}

	public void setNextCRUD(TransparentCRUD nextCRUD) {
		this.nextCRUD = nextCRUD;
	}
	
	@Override
	public <T> T insert(T instance) throws DAOException {
		if (nextCRUD!=null){
			return nextCRUD.insert(instance);
		}
		return null;
	}

	@Override
	public <T> T read(Class entityClass, UUID id) throws DAOException {
		if (nextCRUD!=null){
			return nextCRUD.read(entityClass, id);
		}
		return null;
	}

	@Override
	public <T> void update(T instance) throws DAOException {
		if (nextCRUD!=null){
			nextCRUD.update(instance);
		}
	}

	@Override
	public <T> void delete(T instance) throws DAOException {
		if (nextCRUD!=null){
			nextCRUD.delete(instance);
		}
	}

	

	@Override
	public <T> List<T> select(Class entityClass, DAOFilter filter) throws DAOException {
		if (nextCRUD!=null){
			return nextCRUD.select(entityClass, filter);
		}
		return null;
	}

	@Override
	public <T> List<T> select(String SQLString) throws  DAOException {
		if (nextCRUD!=null){
			return nextCRUD.select(SQLString);
		}
		return null;
	}

	@Override
	public void open(Configuration configuration) throws DAOException {
		if (nextCRUD!=null){
			nextCRUD.open(configuration);
		}
	}

	@Override
	public void close() throws DAOException {
		if (nextCRUD!=null){
			nextCRUD.close();
		}
	}

}
