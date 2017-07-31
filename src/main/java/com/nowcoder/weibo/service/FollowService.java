package com.nowcoder.weibo.service;


import com.nowcoder.weibo.util.JedisAdapter;
import com.nowcoder.weibo.util.RedisKeyUtil;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by lenovo on 17/7/31.
 */
@Service
public class FollowService {
    @Autowired
    JedisAdapter jedisAdapter;


    public boolean follow(int userId, int followeeId) {
        String followerKey = RedisKeyUtil.getFollowerKey(followeeId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId);
        Date date = new Date();
        // 实体的粉丝增加当前用户
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);

        tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
        // 当前用户对这类实体关注+1
        tx.zadd(followeeKey, date.getTime(), String.valueOf(followeeId));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }


    public boolean unfollow(int userId, int followeeId) {
        String followerKey = RedisKeyUtil.getFollowerKey(followeeId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId);
        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        // 实体的粉丝增加当前用户
        tx.zrem(followerKey, String.valueOf(userId));
        // 当前用户对这类实体关注-1
        tx.zrem(followeeKey, String.valueOf(userId));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    public List<Integer> getFollowers(int userId, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(userId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, 0, count));
    }

    public List<Integer> getFollowers(int userId, int offset, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(userId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, offset, offset+count));
    }

    public List<Integer> getFollowees(int userId,int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, 0, count));
    }

    public List<Integer> getFollowees(int userId, int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, offset, offset+count));
    }

    public long getFollowerCount(int userId) {
        String followerKey = RedisKeyUtil.getFollowerKey(userId);
        return jedisAdapter.zcard(followerKey);
    }

    public long getFolloweeCount(int userId) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId);
        return jedisAdapter.zcard(followeeKey);
    }

    private List<Integer> getIdsFromSet(Set<String> idset) {
        List<Integer> ids = new ArrayList<>();
        for (String str : idset) {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }


    public boolean isFollower(int userId, int followeeId) {
        String followerKey = RedisKeyUtil.getFollowerKey(followeeId);
        return jedisAdapter.zscore(followerKey, String.valueOf(userId)) != null;
    }
}
