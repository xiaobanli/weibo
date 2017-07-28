package com.nowcoder.weibo.async.handler;


import com.nowcoder.weibo.async.EventHandler;
import com.nowcoder.weibo.async.EventModel;
import com.nowcoder.weibo.async.EventType;
import com.nowcoder.weibo.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by nowcoder on 2016/7/16.
 */
@Component
public class CommentHandler implements EventHandler {


    @Override
    public void doHandle(EventModel model) {
        System.out.println("这是一个commenthandle");

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.COMMENT);
    }
}
