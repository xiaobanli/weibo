package com.nowcoder.weibo.async;

import com.alibaba.fastjson.JSONObject;

import com.nowcoder.weibo.util.JedisAdapter;
import com.nowcoder.weibo.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nowcoder on 2016/7/16.
 */
@Service
public class EventProducer {
    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model) {
        try {
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return logger.isDebugEnabled();
        }
    }
}
