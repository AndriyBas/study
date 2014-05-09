package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.IntConverter;
import com.oyster.dao.annotation.utils.converter.LongConverter;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 10:49 PM
 */

@Stored(name = "MARK_TBL")
public class Mark {

    @Primary
    @Stored(name = "mark_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "student_id", converter = UUIDConverter.class)
    private UUID studentId;

    @Stored(name = "task_id", converter = UUIDConverter.class)
    private UUID taskId;

    @Stored(name = "mark", converter = IntConverter.class)
    private int mark;


    public Mark() {}

    public Mark(UUID id, UUID studentId, UUID taskId, int mark) {
        this.id = id;
        this.studentId = studentId;
        this.taskId = taskId;
        this.mark = mark;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
