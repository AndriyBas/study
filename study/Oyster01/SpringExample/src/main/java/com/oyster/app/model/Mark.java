package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.IntConverter;
import com.oyster.dao.annotation.utils.converter.LongConverter;

/**
 * @author bamboo
 * @since 4/15/14 10:49 PM
 */

@Stored(name = "MARK_TBL")
public class Mark {

    @Primary
    @Stored(name = "_id", converter = LongConverter.class)
    private long id;

    @Stored(name = "student_id", converter = LongConverter.class)
    private long studentId;

    @Stored(name = "task_id", converter = LongConverter.class)
    private long taskId;

    @Stored(name = "mark", converter = IntConverter.class)
    private int mark;

    public Mark(long id, long studentId, long taskId, int mark) {
        this.id = id;
        this.studentId = studentId;
        this.taskId = taskId;
        this.mark = mark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
