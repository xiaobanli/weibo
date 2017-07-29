package com.nowcoder.weibo.async.handler;

import com.nowcoder.weibo.async.EventHandler;
import com.nowcoder.weibo.async.EventModel;
import com.nowcoder.weibo.async.EventType;
import com.nowcoder.weibo.model.Message;
import com.nowcoder.weibo.model.User;
import com.nowcoder.weibo.service.MessageService;
import com.nowcoder.weibo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/7/29.
 */
@Component
public class LikeHandler implements EventHandler{
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;
    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(3);
        //message.setToId(model.getEntityOwnerId());
        message.setToId(model.getActorId());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName()
                + "赞了你的资讯,http://127.0.0.1:8080/news/" + model.getEntityId());
        message.setCreatedDate(new Date());
        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
