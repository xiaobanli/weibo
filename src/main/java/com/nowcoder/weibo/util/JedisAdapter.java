package com.nowcoder.weibo.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;



/**
 * Created by lenovo on 2017/7/13.
 */
@Service
public class JedisAdapter implements InitializingBean{

    private JedisPool pool=null;
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);


    public static void print(int index, Object obj) {
        System.out.println(String.format("%d,%s", index, obj.toString()));
    }

    public static void main3(String[] args) {
        Jedis jedis = new Jedis();
        jedis.flushAll();
        // get,set

        jedis.set("hello", "world");
        print(1, jedis.get("hello"));

        jedis.rename("hello","newhello");
        print(2,jedis.get("newhello"));
        jedis.setex("hllo2",15,"jjj");
        jedis.set("pv","100");
        jedis.incr("pv");
        jedis.incrBy("pv",5);
        //list
        String listName="listA";
        for(int i=1;i<10;i++)
        {
            jedis.lpush(listName,"a"+String.valueOf(i));
        }
        print(3,jedis.lrange(listName,0,12));
        print(4,jedis.llen(listName));
        print(5,jedis.lpop(listName));
        print(6,jedis.lindex(listName,3));
        jedis.linsert(listName,BinaryClient.LIST_POSITION.AFTER,"a2","xx");
        print(7,jedis.lrange(listName,0,12));
        //hash

        String userKey="userxx";
        jedis.hset(userKey,"name","jim");
        jedis.hset(userKey,"age","23");
        jedis.hset(userKey,"phone","1328453631");
        print(8,jedis.hget(userKey,"name"));
        jedis.hdel(userKey,"phone");
        print(9,jedis.hgetAll(userKey));
        print(10,jedis.hkeys(userKey));
        print(11,jedis.hvals(userKey));
        print(12,jedis.hexists(userKey,"age"));
        jedis.hsetnx(userKey,"school","nju");
        jedis.hsetnx(userKey,"name","nju");
        print(13,jedis.hgetAll(userKey));
        //set


        String likeKeys1="Like1";
        String likeKeys2="Like2";
        for(int i=0;i<10;i++)
        {
            jedis.sadd(likeKeys1,String.valueOf(i));
            jedis.sadd(likeKeys2,String.valueOf(i*i));
        }
        print(13,jedis.smembers(likeKeys1));
        print(14,jedis.smembers(likeKeys2));
        print(15,jedis.sinter(likeKeys1,likeKeys2));
        print(16,jedis.sunion(likeKeys1,likeKeys2));
        print(17,jedis.sdiff(likeKeys1,likeKeys2));
        print(18,jedis.sismember(likeKeys1,"5"));
        jedis.srem(likeKeys1,"5");
        print(18,jedis.smembers(likeKeys1));
        print(19,jedis.scard(likeKeys1));
        jedis.smove(likeKeys2,likeKeys1,"64");
        print(20,jedis.scard(likeKeys1));
        print(21,jedis.smembers(likeKeys1));
        //z
        String zlist="paihang";
        jedis.zadd(zlist,15,"jim");
        jedis.zadd(zlist,22,"org");
        jedis.zadd(zlist,38,"kate");
        jedis.zadd(zlist,76,"jenny");
        jedis.zadd(zlist,98,"jack");
        print(22,jedis.zcard(zlist));
        print(23,jedis.zcount(zlist,60,90));
        print(24,jedis.zscore(zlist,"org"));
        jedis.zincrby(zlist,2,"jenny");
        print(25,jedis.zrange(zlist,0,2));
        print(26,jedis.zrevrange(zlist,0,2));
        for (Tuple tuple:jedis.zrangeByScoreWithScores(zlist,0,100)){
            print(27,tuple.getElement()+":"+String.valueOf(tuple.getScore()));
        }
        print(28,jedis.zrank(zlist,"jim"));
        print(29,jedis.zrevrank(zlist,"jim"));

        JedisPool pool=new JedisPool();
        for(int i=0;i<100;i++)
        {
            Jedis j=pool.getResource();
            j.get("a");
            System.out.println("pool"+i);
            j.close();
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("localhost",6379);

    }
    private Jedis getJedis(){
        return pool.getResource();
    }
    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public void setObject(String key, Object obj) {
        set(key, JSON.toJSONString(obj));
    }
    public <T> T getObject(String key, Class<T> clazz) {
        String value = get(key);
        if (value != null) {
            return JSON.parseObject(value, clazz);
        }
        return null;
    }
}