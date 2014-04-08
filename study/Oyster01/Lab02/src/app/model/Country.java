package app.model;

import java.util.UUID;

import dao.annotation.Primary;
import dao.annotation.Stored;




@Stored(name="COUNTRY")
public class Country {
	
	@Stored(name="ID")
	@Primary 
	private UUID id;
	
	@Stored(name="NAME")
	private String name;
	
	
	public Country() {}

	public Country(UUID id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	
	@Override
	public String toString() {
		return "Country[" + hashCode() + "]: [id=" + id + ", name=" + name
				+ "]";
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
