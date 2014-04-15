package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * class represents a __Position occupied by the worker
 *
 * @author bamboo
 * @since 4/8/14 12:02 AM
 */
@Stored(name = "POSITION")
public class __Position {

    @Stored(name = "_ID", converter = UUIDConverter.class)
    @Primary
    private UUID id;

    @Stored(name = "NAME")
    private String name;

    @Stored(name = "DESCRIPTION")
    private String description;

    /**
     * default constructor
     */
    public __Position() {
    }


    /**
     * Constructor
     *
     * @param id          UUID of the __Position
     * @param name        name of the __Position
     * @param description description of the position
     */
    public __Position(UUID id, String name, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    /**
     * @return UUID of the __Position
     */
    public UUID getId() {
        return id;
    }

    /**
     * set UUID of the __Position
     *
     * @param id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return name of the __Position
     */
    public String getName() {
        return name;
    }

    /**
     * set name of the __Position
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return description  of the __Position
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description  of the __Position
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * show all info about the __Position
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("[id:%s, name:%s, desc:%s]\n", id, name, description);
    }
}
