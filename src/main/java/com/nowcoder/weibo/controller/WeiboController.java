package com.nowcoder.weibo.controller;


import com.nowcoder.weibo.model.*;

import com.nowcoder.weibo.service.CommentService;
import com.nowcoder.weibo.service.QiniuService;
import com.nowcoder.weibo.service.UserService;
import com.nowcoder.weibo.service.WeiboService;
import com.nowcoder.weibo.util.WeiboUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    CommentService commentService;


    @RequestMapping(path = {"/addWeibo/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addWeibo(@RequestParam("image") String image,
                             @RequestParam("content") String content) {
        try {
            Weibo weibo = new Weibo();
            weibo.setCreatedDate(new Date());

            weibo.setImage(image);
            weibo.setContent(content);
            weibo.setCommentCount(0);
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

    @RequestMapping(path = {"/weibo/{weiboId}"}, method = {RequestMethod.GET})
    public String weiboDetail(@PathVariable("weiboId") int weiboId, Model model) {
        Weibo weibo = weiboService.getById(weiboId);

        // 评论
        List<Comment> comments = commentService.getCommentsByEntity(weibo.getId(), EntityType.ENTITY_WEIBO);
        List<ViewObject> commentVOs = new ArrayList<ViewObject>();
        for (Comment comment : comments) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            vo.set("user", userService.getUser(comment.getUserId()));
            commentVOs.add(vo);
        }
        model.addAttribute("comments", commentVOs);
        model.addAttribute("weibo", weibo);
        model.addAttribute("user", userService.getUser(weibo.getUserId()));
        return model.toString();
    }

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("weiboId") int weiboId,
                             @RequestParam("content") String content) {

        try {
            Comment comment = new Comment();
            comment.setUserId(hostHolder.getUser().getId());
            comment.setContent(content);
            comment.setEntityType(EntityType.ENTITY_COMMENT);
            comment.setEntityId(weiboId);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);
            commentService.addComment(comment);

            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            weiboService.updateCommentCount(comment.getEntityId(), count);


        } catch (Exception e) {
            logger.error("提交评论错误" + e.getMessage());
        }
        return commentService.toString()+"添加评论成功";
    }

}






