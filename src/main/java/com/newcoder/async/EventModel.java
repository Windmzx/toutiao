package com.newcoder.async;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by mzx on 17.4.14.
 */
public class EventModel {
    private EventType type;
    private int actorid;
    private int entityType;
    private int entityId;
    private int entityOwener;

    public EventModel() {
    }

    public EventModel(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorid() {
        return actorid;
    }

    public EventModel setActorid(int actorid) {
        this.actorid = actorid;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwener() {
        return entityOwener;
    }

    public EventModel setEntityOwener(int entityOwener) {
        this.entityOwener = entityOwener;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(String key, String value) {
        exts.put(key, value);
        return this;
    }


    private Map<String, String> exts = new HashMap<>();

}
