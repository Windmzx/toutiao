package com.newcoder.service;

import com.newcoder.utils.JedisAdapter;
import com.newcoder.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mzx on 17.4.14.
 */
@SuppressWarnings("JavaDoc")
@Service
public class LikedService {
    @Autowired
    private
    JedisAdapter jedisAdapter;

    /**
     * @param userId
     * @param entityType
     * @param entityId
     * @return 喜欢返回1，不喜欢返回-1，否则0
     */
    public int getLikedStatus(int userId, int entityType, int entityId) {
        String likekey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (jedisAdapter.sismember(likekey, String.valueOf(userId))) {
            return 1;
        }
        String dislikekey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        if (jedisAdapter.sismember(dislikekey, String.valueOf(userId))) {
            return -1;
        }
        return 0;
    }

    public long like(int userId, int entityType, int entityId) {
        String dislikekey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        String likekey = RedisKeyUtil.getLikeKey(entityType, entityId);

        jedisAdapter.srem(dislikekey, String.valueOf(userId));
        jedisAdapter.sadd(likekey, String.valueOf(userId));

        return jedisAdapter.scard(likekey, String.valueOf(userId));
    }

    public long dislike(int userId, int entityType, int entityId) {
        String dislikekey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        String likekey = RedisKeyUtil.getLikeKey(entityType, entityId);

        jedisAdapter.sadd(dislikekey, String.valueOf(userId));
        jedisAdapter.srem(likekey, String.valueOf(userId));

        return jedisAdapter.scard(likekey, String.valueOf(userId));
    }


}
