package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;


/*


    CREATE TABLE `SUBJECT_TBL` (
            `_id` VARCHAR(50),
    `name` VARCHAR(30) UNIQUE,
    PRIMARY KEY (`_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 */

/**
 * Created by bamboo on 07.05.14.
 */
@Stored(name = "SUBJECT_TBL")
public class Subject {

    @Primary
    @Stored(name = "_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "name")
    private String name;

    public Subject() {
    }

    public Subject(UUID id, String name) {
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
}
