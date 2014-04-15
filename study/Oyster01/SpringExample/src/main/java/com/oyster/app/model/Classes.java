package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.IntConverter;
import com.oyster.dao.annotation.utils.converter.LongConverter;

/**
 * @author bamboo
 * @since 4/15/14 10:59 PM
 */

@Stored(name = "CLASSES_TBL")
public class Classes {

    @Primary
    @Stored(name = "_id", converter = LongConverter.class)
    private long id;

    @Stored(name = "subject_id", converter = LongConverter.class)
    private long subjectId;

    @Stored(name = "teacher_id", converter = LongConverter.class)
    private long teacherId;

    @Stored(name = "building", converter = IntConverter.class)
    private int building;

    @Stored(name = "audience", converter = IntConverter.class)
    private int audience;

    @Stored(name = "date", converter = LongConverter.class)
    private long date;

    public Classes(long id, long subjectId, long teacherId, int building, int audience, long date) {
        this.id = id;
        this.subjectId = subjectId;
        this.teacherId = teacherId;
        this.building = building;
        this.audience = audience;
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
