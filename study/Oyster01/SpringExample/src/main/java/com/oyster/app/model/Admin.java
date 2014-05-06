package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.LongConverter;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 11:03 PM
 */

@Stored(name = "ADMIN_TBL")
public class Admin {
    @Primary
    @Stored(name = "_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "profile_id", converter = UUIDConverter.class)
    private UUID profileId;

    @Stored(name = "worker_info_id", converter = UUIDConverter.class)
    private UUID workerInfoId;

    public Admin(UUID id, UUID profileId, UUID workerInfoId) {
        this.id = id;
        this.profileId = profileId;
        this.workerInfoId = workerInfoId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getWorkerInfoId() {
        return workerInfoId;
    }

    public void setWorkerInfoId(UUID workerInfoId) {
        this.workerInfoId = workerInfoId;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }
}
