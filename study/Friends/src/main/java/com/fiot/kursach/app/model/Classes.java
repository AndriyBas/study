package com.fiot.kursach.app.model;

import com.fiot.kursach.dao.annotation.Primary;
import com.fiot.kursach.dao.annotation.Stored;
import com.fiot.kursach.dao.annotation.utils.converter.IntConverter;
import com.fiot.kursach.dao.annotation.utils.converter.UUIDConverter;
import java.util.UUID;

/**
 * Граничний клас, що представляє таблицю CLASSES_TBL у базі даних
 *
 * @author bamboo
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
