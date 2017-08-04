package com.nowcoder.weibo.controller;


import com.nowcoder.weibo.model.ViewObject;
import com.nowcoder.weibo.model.Weibo;
import com.nowcoder.weibo.service.FollowService;
import com.nowcoder.weibo.service.SearchService;
import com.nowcoder.weibo.service.UserService;
import com.nowcoder.weibo.service.WeiboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import java.util.List;

/**
 * Created by nowcoder on 2016/7/24.
 */
@Controller
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    SearchService searchService;

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @Autowired
    WeiboService weiboService;

    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET})
    public String search(Model model, @RequestParam("keyword") String keyword,
                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "count", defaultValue = "10") int count) {
        try {
            List<Weibo> weiboList = searchService.searchWeibo(keyword, offset, count,
                    "<em>", "</em>");
            List<ViewObject> vos = new ArrayList<>();
            for (Weibo weibo : weiboList) {
                Weibo wei = weiboService.getById(weibo.getId());
                ViewObject vo = new ViewObject();
                if (weibo.getContent() != null) {
                    wei.setContent(weibo.getContent());
                }

                vo.set("weibo", wei);
                vo.set("followCount", followService.getFollowerCount(wei.getId()));
                vo.set("user", userService.getUser(wei.getUserId()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error("搜索微博失败" + e.getMessage());
        }
        return model.toString();
    }
}
