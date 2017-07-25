//package com.nowcoder.weibo.controller;
//
//import com.nowcoder.weibo.dao.CommentDAO;
//import com.nowcoder.weibo.model.Comment;
//import com.nowcoder.weibo.model.EntityType;
//import com.nowcoder.weibo.model.HostHolder;
//import com.nowcoder.weibo.service.CommentService;
//import com.nowcoder.weibo.service.MessageService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Date;
//
///**
// * Created by lenovo on 2017/7/25.
// */
//@Controller
//public class CommentController {
//    @Autowired
//    CommentDAO commentDAO;
//    @Autowired
//    HostHolder hostHolder;
//    @Autowired
//    CommentService commentService;
//    @Autowired
//    MessageService messageService;
//    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
//    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
//    public String addComment(@RequestParam("messageId") int messageId,
//                             @RequestParam("content") String content) {
//        if(hostHolder)
//        try {
//            Comment comment = new Comment();
//            comment.setUserId(hostHolder.getUser().getId());
//            comment.setContent(content);
//            comment.setEntityType(EntityType.ENTITY_MESSAGE);
//            comment.setEntityId(messageId);
//            comment.setCreatedDate(new Date());
//            comment.setStatus(0);
//            commentService.addComment(comment);
//
//            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
//            messageService.updateCommentCount(comment.getEntityId(), count);
//
//
//        } catch (Exception e) {
//            logger.error("提交评论错误" + e.getMessage());
//        }
//        return "redirect:/news/" + String.valueOf(messageId);
//    }
//
//}
