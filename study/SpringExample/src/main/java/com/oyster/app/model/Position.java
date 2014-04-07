package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * class represents a Position occupied by the worker
 *
 * @author bamboo
 * @since 4/8/14 12:02 AM
 */
@Stored(name = "POSITION")
public class Position {

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
    public Position() {
    }

    /**
     * @return UUID of the Position
     */
    public UUID getId() {
        return id;
    }

    /**
     * set UUID of the Position
     *
     * @param id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return name of the Position
     */
    public String getName() {
        return name;
    }

    /**
     * set name of the Position
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return description  of the Position
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description  of the Position
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
