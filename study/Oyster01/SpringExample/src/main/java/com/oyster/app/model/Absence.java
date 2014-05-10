package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 10:54 PM
 */

/*

    CREATE TABLE `ABSENCE_TBL` (
            `absence_id` VARCHAR(50),
    `group_id` VARCHAR(50),
    `class_id`  VARCHAR(50),
    PRIMARY KEY (`absence_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
