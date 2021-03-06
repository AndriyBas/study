package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.IntConverter;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 10:59 PM
 */


/*
    CREATE TABLE `CLASSES_TBL` (
            `classes_id` VARCHAR(50),
    `subject_id` VARCHAR(50),
    `teacher_id`  VARCHAR(50),
    `audience`  INT UNSIGNED ZEROFILL,
    `class_date`  INT UNSIGNED ZEROFILL,
    PRIMARY KEY (`classes_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

    @Stored(name = "audience", converter = IntConverter.class)
    private int audience;

    @Stored(name = "class_date", converter = IntConverter.class)
    private int time;

    private Subject subject;

    private Teacher teacher;

    public Classes() {
    }

    public Classes(UUID id, UUID subjectId, UUID teacherId, int audience, int time) {
        this.id = id;
        this.subjectId = subjectId;
        this.teacherId = teacherId;

        this.audience = audience;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
