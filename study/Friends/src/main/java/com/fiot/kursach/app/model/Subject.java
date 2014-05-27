package com.fiot.kursach.app.model;

import com.fiot.kursach.dao.annotation.Primary;
import com.fiot.kursach.dao.annotation.Stored;
import com.fiot.kursach.dao.annotation.utils.converter.UUIDConverter;
import java.util.UUID;


/**
 * Граничний клас, що представляє таблицю SUBJECT_TBL у базі даних
 *
 * @author bamboo
 */

@Stored(name = "SUBJECT_TBL")
public class Subject {

    @Primary
    @Stored(name = "subject_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "subject_name")
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

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Subject) {
            return ((Subject) obj).toString().equals(this.toString());
        } else if (obj instanceof String) {
            return ((String) obj).equals(this.toString());
        }
        return this.equals(obj);
    }
}
