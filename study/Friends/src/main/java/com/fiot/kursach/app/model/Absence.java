package com.fiot.kursach.app.model;

import com.fiot.kursach.dao.annotation.Primary;
import com.fiot.kursach.dao.annotation.Stored;
import com.fiot.kursach.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * Граничний клас, що представляє таблицю ABSENCE_TBL у базі даних
 *
 * @author bamboo
 */
@Stored(name = "ABSENCE_TBL")
public class Absence {
    @Primary
    @Stored(name = "absence_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "group_id", converter = UUIDConverter.class)
    private UUID groupId;

    @Stored(name = "class_id", converter = UUIDConverter.class)
    private UUID classId;

    public Absence() {
    }

    public Absence(UUID id, UUID groupId, UUID classId) {
        this.id = id;
        this.groupId = groupId;
        this.classId = classId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public UUID getClassId() {
        return classId;
    }

    public void setClassId(UUID classId) {
        this.classId = classId;
    }
}
