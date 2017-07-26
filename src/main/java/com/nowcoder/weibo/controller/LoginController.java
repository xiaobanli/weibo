package com.nowcoder.weibo.controller;


import com.nowcoder.weibo.service.UserService;
import com.nowcoder.weibo.util.WeiboUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Created by nowcoder on 2016/7/2.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/register"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String register(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value="remember", defaultValue = "0") int remember,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");//设置cookie全站有效
                if (remember > 0) {
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return WeiboUtil.getJSONString(0, "注册成功");
            } else {
                return WeiboUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return WeiboUtil.getJSONString(1, "注册异常");
        }
    }

    @RequestMapping(path = {"/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value="rember", defaultValue = "0") int remember) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (remember > 0) {
                    cookie.setMaxAge(3600*24*5);
                }
                return WeiboUtil.getJSONString(0, "登录成功");
            } else {
                return WeiboUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("登录失败" + e.getMessage());
            return WeiboUtil.getJSONString(1, "登录失败");
        }
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }


}
