package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.LongConverter;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 10:51 PM
 */
@Stored(name = "TASK_TBL")
public class Task {

    @Primary
    @Stored(name = "_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "subject_id", converter = UUIDConverter.class)
    private UUID subjectId;

    @Stored(name = "desc")
    private String description;

    @Stored(name = "date", converter = LongConverter.class)
    private long date;

    public Task() {
    }

    public Task(UUID id, UUID subjectId, String description, long date) {
        this.id = id;
        this.subjectId = subjectId;
        this.description = description;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(UUID subjectId) {
        this.subjectId = subjectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
