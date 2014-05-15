package com.fiot.app.model;

import com.fiot.dao.annotation.Primary;
import com.fiot.dao.annotation.Stored;
import com.fiot.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * Граничний клас, що представляє таблицю FACULTY_TBL у базі даних
 *
 * @author bamboo
 */
@Stored(name = "FACULTY_TBL")
public class Faculty {

    @Primary
    @Stored(name = "faculty_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "faculty_name")
    private String name;

    public Faculty() {
    }

    public Faculty(UUID id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return getName();
    }
}
