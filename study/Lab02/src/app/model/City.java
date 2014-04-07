package app.model;

import dao.annotation.Primary;
import dao.annotation.Stored;
import dao.annotation.utils.converter.UUIDConverter;
import dao.annotation.utils.converter.intConverter;

import java.util.UUID;


/**
 * @author
 */
@Stored(name = "CITY")
public class City {
    @Stored(name = "ID", converter = UUIDConverter.class)
    @Primary
    private UUID id;

    @Stored(name = "NAME")
    private String name;

    @Stored(name = "COUNTRY_ID", converter = UUIDConverter.class)
    private UUID country;

    @Stored(name = "COMMENT")
    private String comment;

    @Stored(name = "RATING", converter = intConverter.class)
    private int n;


    public City() {
    }

    public City(UUID id, String name, UUID country) {
        super();
        this.id = id;
        this.name = name;
        this.country = country;
        this.comment = "";
    }


    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }


    @Override
    public String toString() {
        return "City[" + hashCode() + "]: [id=" + id + ", name=" + name
                + ", country=" + country + ", comment=" + comment + ", n=" + n
                + "]";
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public UUID getCountry() {
        return country;
    }

    public void setCountry(UUID country) {
        this.country = country;
    }

}
