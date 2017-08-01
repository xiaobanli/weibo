package com.nowcoder.weibo.controller;


import com.nowcoder.weibo.model.HostHolder;
import com.nowcoder.weibo.model.Message;
import com.nowcoder.weibo.model.User;
import com.nowcoder.weibo.model.ViewObject;
import com.nowcoder.weibo.service.MessageService;
import com.nowcoder.weibo.service.UserService;
import com.nowcoder.weibo.util.WeiboUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nowcoder on 2016/7/9.
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model) {
        try {
            int localUserId = hostHolder.getUser().getId();
            if (hostHolder.getUser() != null) {

                List<ViewObject> conversations = new ArrayList<ViewObject>();
                List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
                for (Message msg : conversationList) {
                    ViewObject vo = new ViewObject();
                    vo.set("conversation", msg);
                    int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
                    User user = userService.getUser(targetId);
                    vo.set("user", user);
                    vo.set("unread", messageService.getConvesationUnreadCount(localUserId, msg.getConversationId()));
                    conversations.add(vo);
                }
                model.addAttribute("conversations", conversations);
            }
            else{
                return WeiboUtil.getJSONString(1, "用户不存在");
            }
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model, @Param("conversationId") String conversationId) {
        try {
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> msgdetail = new ArrayList<ViewObject>();
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                User user = userService.getUser(msg.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                msgdetail.add(vo);
            }
            model.addAttribute("msgdetails", msgdetail);

        } catch (Exception e) {
            logger.error("获取站内信详情失败" + e.getMessage());
            return WeiboUtil.getJSONString(1, "站内信详情失败");
        }
        return "";
    }


    @RequestMapping(path = {"/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message message = new Message();
            message.setContent(content);
            message.setFromId(fromId);
            message.setToId(toId);
            message.setCreatedDate(new Date());
            message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));

            messageService.addMessage(message);
            return WeiboUtil.getJSONString(1,message.getContent()+"发送成功");
        } catch (Exception e) {
            logger.error("站内信发送失败" + e.getMessage());
            return WeiboUtil.getJSONString(1, "站内信添加失败");
        }
    }
}
