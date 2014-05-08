package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 10:47 PM
 */

/*

    CREATE TABLE `GROUP_TBL` (
            `_id` VARCHAR(50),
    `name` VARCHAR(30) UNIQUE,
    `faculty_id`  VARCHAR(50),
    PRIMARY KEY (`_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 */
@Stored(name = "GROUP_TBL")
public class Group {

    @Primary
    @Stored(name = "_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "faculty_id", converter = UUIDConverter.class)
    private UUID facultyId;

    @Stored(name = "name")
    private String name;

    private Faculty faculty;

    public Group() {
    }

    public Group(UUID id, UUID facultyId, String name) {
        this.id = id;
        this.facultyId = facultyId;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(UUID facultyId) {
        this.facultyId = facultyId;
    }


    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return getName();
    }
}
