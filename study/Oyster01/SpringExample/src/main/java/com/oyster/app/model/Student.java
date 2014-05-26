package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.IntConverter;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * Граничний клас, що представляє таблицю STUDENT_TBL у базі даних
 *
 * @author bamboo
 */

@Stored(name = "STUDENT_TBL")
public class Student implements IProfile {

    @Primary
    @Stored(name = "student_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "profile_id", converter = UUIDConverter.class)
    private UUID profileId;


    @Stored(name = "group_id", converter = UUIDConverter.class)
    private UUID groupId;

    @Stored(name = "course", converter = IntConverter.class)
    private int course;

    @Stored(name = "book_num", converter = IntConverter.class)
    private int bookNum;

    private Profile profile;

    private Group group;

    public Student() {
    }

    public Student(UUID id, UUID profileId, UUID groupId, int course, int bookNum) {
        this.id = id;
        this.profileId = profileId;
        this.groupId = groupId;
        this.course = course;
        this.bookNum = bookNum;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }


    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }


    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return getProfile().toString();
    }
}
