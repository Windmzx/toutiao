package com.newcoder.controller;

import com.newcoder.service.UserService;
import com.newcoder.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by mzx on 17.4.7.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping(path = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rember", defaultValue = "0") int rember,
                        HttpServletResponse response) {

        Map<String, Object> map = userService.login(username, password, rember);
        //返回的map里有ticket说明 登陆成功
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            return ServiceUtil.getJson(0, "登陆成功");
        }
        return ServiceUtil.getJson(1, map);
    }

    @RequestMapping(path = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }
}
