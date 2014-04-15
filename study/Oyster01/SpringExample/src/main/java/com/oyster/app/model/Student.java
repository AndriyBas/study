package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.IntConverter;
import com.oyster.dao.annotation.utils.converter.LongConverter;

/**
 * @author bamboo
 * @since 4/15/14 10:36 PM
 */

@Stored(name = "STUDENT_TBL")
public class Student {

    @Primary
    @Stored(name = "_id", converter = LongConverter.class)
    private long id;

    @Stored(name = "profile_id", converter = LongConverter.class)
    private long profileId;

    @Stored(name = "faculty_id", converter = LongConverter.class)
    private long facultyId;

    @Stored(name = "group_id", converter = LongConverter.class)
    private long groupId;

    @Stored(name = "course", converter = IntConverter.class)
    private int course;

    @Stored(name = "comment")
    private String comment;

    @Stored(name = "book_num", converter = IntConverter.class)
    private int bookNum;


    public Student(long id, long profileId, long facultyId, long groupId, int course, String comment, int bookNum) {
        this.id = id;
        this.profileId = profileId;
        this.facultyId = facultyId;
        this.groupId = groupId;
        this.course = course;
        this.comment = comment;
        this.bookNum = bookNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(long facultyId) {
        this.facultyId = facultyId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }
}
