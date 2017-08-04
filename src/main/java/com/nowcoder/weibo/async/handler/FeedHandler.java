package com.nowcoder.weibo.async.handler;

import com.alibaba.fastjson.JSONObject;

import com.nowcoder.weibo.async.EventHandler;
import com.nowcoder.weibo.async.EventModel;
import com.nowcoder.weibo.async.EventType;
import com.nowcoder.weibo.model.EntityType;
import com.nowcoder.weibo.model.Feed;
import com.nowcoder.weibo.model.User;
import com.nowcoder.weibo.model.Weibo;
import com.nowcoder.weibo.service.FeedService;
import com.nowcoder.weibo.service.FollowService;
import com.nowcoder.weibo.service.UserService;
import com.nowcoder.weibo.service.WeiboService;
import com.nowcoder.weibo.util.JedisAdapter;
import com.nowcoder.weibo.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by nowcoder on 2016/7/30.
 */
@Component
public class FeedHandler implements EventHandler {
    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @Autowired
    FeedService feedService;

    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    WeiboService weiboService;




    private String buildFeedData(EventModel model) {
        Map<String, String> map = new HashMap<String ,String>();
        // 触发用户是通用的
        User actor = userService.getUser(model.getActorId());
        if (actor == null) {
            return null;
        }
        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());

        if (model.getType() == EventType.COMMENT ||
                (model.getType() == EventType.FOLLOW  && model.getEntityType() == EntityType.ENTITY_WEIBO)) {
            Weibo weibo = weiboService.getById(model.getEntityId());
            if (weibo == null) {
                return null;
            }
            map.put("weiboId", String.valueOf(weibo.getId()));
            map.put("weiboContent", weibo.getContent());
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    @Override
    public void doHandle(EventModel model) {
        // 为了测试，把model的userId随机一下
        Random r = new Random();
        model.setActorId(1+r.nextInt(10));

        // 构造一个新鲜事
        Feed feed = new Feed();
        feed.setCreatedDate(new Date());
        feed.setType(model.getType().getValue());
        feed.setUserId(model.getActorId());
        feed.setData(buildFeedData(model));
        if (feed.getData() == null) {
            // 不支持的feed
            return;
        }
        feedService.addFeed(feed);

        // 获得所有粉丝
        List<Integer> followers = followService.getFollowers( model.getActorId(), Integer.MAX_VALUE);
        // 系统队列
        followers.add(0);
        // 给所有粉丝推事件
        for (int follower : followers) {
            String timelineKey = RedisKeyUtil.getTimelineKey(follower);
            jedisAdapter.lpush(timelineKey, String.valueOf(feed.getId()));
            // 限制最长长度，如果timelineKey的长度过大，就删除后面的新鲜事
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT, EventType.FOLLOW});
    }
}
