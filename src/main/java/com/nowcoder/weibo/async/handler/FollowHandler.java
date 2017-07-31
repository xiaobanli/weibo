package com.nowcoder.weibo.async.handler;


import com.nowcoder.weibo.async.EventHandler;
import com.nowcoder.weibo.async.EventModel;
import com.nowcoder.weibo.async.EventType;
import com.nowcoder.weibo.model.Message;
import com.nowcoder.weibo.model.User;
import com.nowcoder.weibo.service.MessageService;
import com.nowcoder.weibo.service.UserService;
import com.nowcoder.weibo.util.WeiboUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by nowcoder on 2016/7/30.
 */
@Component
public class FollowHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WeiboUtil.SYSTEM_USERID);
        message.setToId(model.getActorId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName() + "关注了你");


        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
