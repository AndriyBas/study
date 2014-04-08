package app.test;

import java.util.List;
import java.util.UUID;

import configuration.Configuration;
import dao.CRUDInterface;
import dao.DAOFilter;
import dao.DAOProviderFactory;
import dao.TransparentCRUD;
import dao.cache.MemoryCache;
import dao.exception.DAOException;
import dao.exception.DAOInvalidProviderTypeException;
import dao.keygenerator.KeyGenerator;

import app.model.City;
import app.model.Country;

public class TestPrimaryKeyGeneration {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		City city = new City(null, "Kiev", null);
		City city1 = new City(null, "Kharkov", null);
		City city2 = new City(null, "Odessa", null);
		
		Country country = new Country(UUID.randomUUID(), "Ukraine");
		city.setCountry(country.getId());
		city1.setCountry(country.getId());
		city2.setCountry(country.getId());
		
		
		Configuration conf = new Configuration();
		
		conf.setProperty("dao.provider.type", "MEMORY");
//		conf.setProperty("dao.provider.type", "JSON");
//		conf.setProperty("dao.storage.path","./test/storage/json/");
//		conf.setProperty("dao.storage.encoding","utf8");
//		conf.setProperty("dao.storage.extention",".json");

		CRUDInterface cache;
		try {
			cache = new DAOProviderFactory().getDAOProvider(conf);
			cache.open(conf);
			cache.insert(city);
			cache.insert(city1);
			cache.insert(city2);
			
			cache.insert(country);
//			System.out.println(cache);
			UUID cityId = city.getId();
			City c = cache.read(City.class, cityId);
			System.out.println(c);
			
			
			List<City> s = cache.select(City.class,
					new DAOFilter() {
					@Override
					public <T> boolean accept(T entity) {
						if(((City)entity).getName().startsWith("K")){
							return true;
						}
						return false;
					}
				});
			
			System.out.println(s);
			
//			s = cache.select("");
//			System.out.println(s);
			cache.close();

		} catch (DAOException e) {
			
			e.printStackTrace();
		}
	}
		
}
