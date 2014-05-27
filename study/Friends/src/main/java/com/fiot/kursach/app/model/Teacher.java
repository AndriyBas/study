package com.fiot.kursach.app.model;

import com.fiot.kursach.dao.annotation.Primary;
import com.fiot.kursach.dao.annotation.Stored;
import com.fiot.kursach.dao.annotation.utils.converter.UUIDConverter;
import java.util.UUID;

/**
 * Граничний клас, що представляє таблицю TEACHER_TBL у базі даних
 *
 * @author bamboo
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Teacher) {
            return ((Teacher) obj).toString().equals(this.toString());
        } else if (obj instanceof String) {
            return ((String) obj).equals(this.toString());
        }
        return this.equals(obj);
    }
}
