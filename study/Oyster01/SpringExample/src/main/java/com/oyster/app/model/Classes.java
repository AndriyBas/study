package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.IntConverter;
import com.oyster.dao.annotation.utils.converter.LongConverter;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 10:59 PM
 */

@Stored(name = "CLASSES_TBL")
public class Classes {

    @Primary
    @Stored(name = "classes_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "subject_id", converter = UUIDConverter.class)
    private UUID subjectId;

    @Stored(name = "teacher_id", converter = UUIDConverter.class)
    private UUID teacherId;

    @Stored(name = "building", converter = IntConverter.class)
    private int building;

    @Stored(name = "audience", converter = IntConverter.class)
    private int audience;

    @Stored(name = "class_date", converter = LongConverter.class)
    private long date;

    public Classes() {
    }

    public Classes(UUID id, UUID subjectId, UUID teacherId, int building, int audience, long date) {
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

    public UUID getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(UUID teacherId) {
        this.teacherId = teacherId;
    }

    public UUID getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(UUID subjectId) {
        this.subjectId = subjectId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
