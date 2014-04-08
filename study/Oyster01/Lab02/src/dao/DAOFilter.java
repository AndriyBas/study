package dao;

public interface DAOFilter {
	public <T> 	boolean accept(T entity);
}
