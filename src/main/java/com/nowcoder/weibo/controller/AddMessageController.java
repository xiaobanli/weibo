package com.nowcoder.weibo.controller;


import com.nowcoder.weibo.model.HostHolder;
import com.nowcoder.weibo.model.Message;
import com.nowcoder.weibo.service.MessageService;
import com.nowcoder.weibo.service.QiniuService;
import com.nowcoder.weibo.service.UserService;
import com.nowcoder.weibo.util.WeiboUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * Created by nowcoder on 2016/7/2.
 */
@Controller
public class AddMessageController {
    private static final Logger logger = LoggerFactory.getLogger(AddMessageController.class);
    @Autowired
    MessageService messageService;

    @Autowired
    QiniuService qiniuService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;


    @RequestMapping(path = {"/addMessage/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("image") String image,
                          @RequestParam("content") String content) {
        try {
            Message message=new Message();
            message.setCreatedDate(new Date());

            message.setImage(image);
            message.setContent(content);

            if (hostHolder.getUser() != null) {
                message.setUserId(hostHolder.getUser().getId());
            } else {
                // 设置一个匿名用户
                message.setUserId(3);
            }
            messageService.addMessage(message);
            return WeiboUtil.getJSONString(0,"微博发布成功");
        } catch (Exception e) {
            logger.error("微博添加失败" + e.getMessage());
            return WeiboUtil.getJSONString(1, "微博发布失败");
        }
    }
}
