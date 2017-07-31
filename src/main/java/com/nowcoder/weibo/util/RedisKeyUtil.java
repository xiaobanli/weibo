package com.nowcoder.weibo.util;

/**
 * Created by nowcoder on 2016/7/13.
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT = "EVENT";

    private static String BIZ_FOLLOWER = "FOLLOWER";
    // 关注对象
    private static String BIZ_FOLLOWEE = "FOLLOWEE";

    public static String getEventQueueKey() {
        return BIZ_EVENT;
    }

    public static String getLikeKey(int entityId, int entityType) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityId, int entityType) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }
    public static String getFollowerKey(int userId) {
        return BIZ_FOLLOWER + SPLIT + String.valueOf(userId);
    }

    // 每个用户对某类实体的关注key
    public static String getFolloweeKey(int userId) {
        return BIZ_FOLLOWEE + SPLIT + String.valueOf(userId);
    }
}
