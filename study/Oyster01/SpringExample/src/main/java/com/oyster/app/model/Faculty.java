package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/*


    CREATE TABLE `FACULTY_TBL` (
            `faculty_id` VARCHAR(50),
    `faculty_name` VARCHAR(30) UNIQUE,
    PRIMARY KEY (`faculty_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 */

/**
 * @author bamboo
 * @since 4/15/14 10:44 PM
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
