package com.newcoder.controller;

import com.newcoder.service.UserService;
import com.newcoder.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by mzx on 17.4.7.
 */
@Controller
public class RegisterController {

    @Autowired
    UserService userService;


    @ResponseBody
    @RequestMapping(path = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam(value = "rember", defaultValue = "0") int rember,
                           HttpServletResponse response) {

        try {
            Map<String, Object> map = userService.register(username, password, rember);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                return ServiceUtil.getJson(0, "注册成功");
            } else {
                return ServiceUtil.getJson(1, map);
            }
        } catch (Exception e) {

            return ServiceUtil.getJson(1, "注册异常");

        }

    }

}
