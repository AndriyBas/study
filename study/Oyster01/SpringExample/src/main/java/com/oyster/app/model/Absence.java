package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.LongConverter;

/**
 * @author bamboo
 * @since 4/15/14 10:54 PM
 */

@Stored(name = "ABSENCE_TBL")
public class Absence {
    @Primary
    @Stored(name = "_id", converter = LongConverter.class)
    private long id;

    @Stored(name = "group_id", converter = LongConverter.class)
    private long groupId;

    @Stored(name = "class_id", converter = LongConverter.class)
    private long classId;

    public Absence(long id, long groupId, long classId) {
        this.id = id;
        this.groupId = groupId;
        this.classId = classId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }
}
