package com.nowcoder.weibo.controller;


import com.nowcoder.weibo.async.EventModel;
import com.nowcoder.weibo.async.EventProducer;
import com.nowcoder.weibo.async.EventType;
import com.nowcoder.weibo.model.EntityType;
import com.nowcoder.weibo.model.HostHolder;
import com.nowcoder.weibo.model.User;
import com.nowcoder.weibo.model.ViewObject;
import com.nowcoder.weibo.service.FollowService;
import com.nowcoder.weibo.service.UserService;
import com.nowcoder.weibo.util.WeiboUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.*;

/**
 * Created by nowcoder on 2016/7/30.
 */
@Controller
public class FollowController {
    @Autowired
    FollowService followService;


    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/follow"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String follow(@RequestParam("userId") int userId) {
        if (hostHolder.getUser() == null) {
            return WeiboUtil.getJSONString(1,"用户未登录");
        }

        boolean ret = followService.follow(hostHolder.getUser().getId(),userId);

        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(userId)
                .setEntityType(EntityType.ENTITY_FOLLOW).setEntityOwnerId(userId));

        // 返回关注的人数
        return WeiboUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId())));
    }

    @RequestMapping(path = {"/unfollow"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unfollow(@RequestParam("userId") int userId) {
        if (hostHolder.getUser() == null) {
            return WeiboUtil.getJSONString(1,"用户未登录");
        }

        boolean ret = followService.unfollow(hostHolder.getUser().getId(),userId);


        // 返回关注的人数
        return WeiboUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId())));
    }


    @RequestMapping(path = {"/user/{uid}/followers"}, method = {RequestMethod.GET})
    public String followers(Model model, @PathVariable("uid") int userId) {
        List<Integer> followerIds = followService.getFollowers(userId, 0, 10);
        if (hostHolder.getUser() != null) {
            model.addAttribute("followers", getUsersInfo(hostHolder.getUser().getId(), followerIds));
        } else {
            model.addAttribute("followers", getUsersInfo(0, followerIds));
        }
        model.addAttribute("followerCount", followService.getFollowerCount( userId));
        model.addAttribute("curUser", userService.getUser(userId));
        return WeiboUtil.getJSONString(0,model.toString());
    }

    @RequestMapping(path = {"/user/{uid}/followees"}, method = {RequestMethod.GET})
    public String followees(Model model, @PathVariable("uid") int userId) {
        List<Integer> followeeIds = followService.getFollowees(userId,  0, 10);

        if (hostHolder.getUser() != null) {
            model.addAttribute("followees", getUsersInfo(hostHolder.getUser().getId(), followeeIds));
        } else {
            model.addAttribute("followees", getUsersInfo(0, followeeIds));
        }
        model.addAttribute("followeeCount", followService.getFolloweeCount(userId));
        model.addAttribute("curUser", userService.getUser(userId));
        return WeiboUtil.getJSONString(0,model.toString());
    }

    private List<ViewObject> getUsersInfo(int localUserId, List<Integer> userIds) {
        List<ViewObject> userInfos = new ArrayList<ViewObject>();
        for (Integer uid : userIds) {
            User user = userService.getUser(uid);
            if (user == null) {
                continue;
            }
            ViewObject vo = new ViewObject();
            vo.set("user", user);
            if (localUserId != 0) {
                vo.set("followed", followService.isFollower(localUserId, uid));
            } else {
                vo.set("followed", false);
            }
            userInfos.add(vo);
        }
        return userInfos;
    }
}
