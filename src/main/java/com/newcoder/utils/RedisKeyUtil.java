package com.newcoder.utils;

/**
 * Created by mzx on 17.4.14.
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String LIKE = "LIKE";
    private static String DIS_LIKE = "DISLIKE";
    private static String EVENT = "EVENT";

    public static String getLikeKey(int entityTye, int entityId) {
        return LIKE + SPLIT + String.valueOf(entityTye) + SPLIT + String.valueOf(entityId);
    }


    public static String getDisLikeKey(int entityTye, int entityId) {
        return DIS_LIKE + SPLIT + String.valueOf(entityTye) + SPLIT + String.valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return EVENT;
    }
}
