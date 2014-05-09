package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 11:03 PM
 */

/*

    CREATE TABLE `TEACHER_TBL` (
            `teacher_id` VARCHAR(50),
    `profile_id` VARCHAR(50) UNIQUE,
    `worker_info_id` VARCHAR(50) UNIQUE,
    PRIMARY KEY (`teacher_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 */
@Stored(name = "TEACHER_TBL")
public class Teacher implements IProfile {
    @Primary
    @Stored(name = "teacher_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "profile_id", converter = UUIDConverter.class)
    private UUID profileId;

    @Stored(name = "worker_info_id", converter = UUIDConverter.class)
    private UUID workerInfoId;

    private Profile profile;

    private WorkerInfo workerInfo;

    public Teacher() {
    }

    public Teacher(UUID id, UUID profileId, UUID workerInfoId) {
        this.id = id;
        this.profileId = profileId;
        this.workerInfoId = workerInfoId;
    }

    public UUID getWorkerInfoId() {
        return workerInfoId;
    }

    public void setWorkerInfoId(UUID workerInfoId) {
        this.workerInfoId = workerInfoId;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public WorkerInfo getWorkerInfo() {
        return workerInfo;
    }

    public void setWorkerInfo(WorkerInfo workerInfo) {
        this.workerInfo = workerInfo;
    }

    @Override
    public String toString() {
        return getProfile().toString();
    }
}
