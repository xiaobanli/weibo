package com.nowcoder.weibo.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.List;

/**
 * Created by nowcoder on 2016/7/13.
 */
@Service
public class JedisAdapter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);


    public static void print(int index, Object obj) {
        System.out.println(String.format("%d,%s", index, obj.toString()));
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.flushAll();
        // get,set

        jedis.set("hello", "world");
        print(1, jedis.get("hello"));


    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}