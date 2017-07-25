package com.nowcoder.weibo.controller;


import com.nowcoder.weibo.model.HostHolder;

import com.nowcoder.weibo.model.Weibo;
import com.nowcoder.weibo.service.QiniuService;
import com.nowcoder.weibo.service.UserService;
import com.nowcoder.weibo.service.WeiboService;
import com.nowcoder.weibo.util.WeiboUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.Date;



@Controller
public class WeiboController {
    private static final Logger logger = LoggerFactory.getLogger(WeiboController.class);
    @Autowired
    WeiboService weiboService;

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
            Weibo weibo = new Weibo();
            weibo.setCreatedDate(new Date());

            weibo.setImage(image);
            weibo.setContent(content);

            if (hostHolder.getUser() != null) {
                weibo.setUserId(hostHolder.getUser().getId());
            } else {
                // 设置一个匿名用户
                weibo.setUserId(3);
            }
            weiboService.addWeibo(weibo);
            return WeiboUtil.getJSONString(0, "微博发布成功");
        } catch (Exception e) {
            logger.error("微博添加失败" + e.getMessage());
            return WeiboUtil.getJSONString(1, "微博发布失败");
        }
    }
}

