package com.nowcoder.weibo.controller;


import com.nowcoder.weibo.async.EventModel;
import com.nowcoder.weibo.async.EventProducer;
import com.nowcoder.weibo.async.EventType;
import com.nowcoder.weibo.model.EntityType;
import com.nowcoder.weibo.model.HostHolder;
import com.nowcoder.weibo.model.Weibo;
import com.nowcoder.weibo.service.LikeService;
import com.nowcoder.weibo.service.WeiboService;
import com.nowcoder.weibo.util.WeiboUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by nowcoder on 2016/7/13.
 */
@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    WeiboService weiboService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@Param("weiboId") int weiboId) {
        //long likeCount = likeService.like(2, EntityType.ENTITY_WEIBO, weiboId);
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_WEIBO, weiboId);
        // 更新喜欢数;默认设成当前用户id为2；登录时还要hostholder一下
        Weibo weibo = weiboService.getById(weiboId);
        weiboService.updateLikeCount(weiboId, (int) likeCount);

        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityId(weiboId)
                .setEntityType(EntityType.ENTITY_WEIBO).setEntityOwnerId(weibo.getUserId()));

        return WeiboUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@Param("weiboId") int weiboId) {
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_WEIBO, weiboId);
        // 更新喜欢数
        weiboService.updateLikeCount(weiboId, (int) likeCount);
        return WeiboUtil.getJSONString(0, String.valueOf(likeCount));
    }
}
