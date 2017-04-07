package com.newcoder.controller;

import com.newcoder.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by mzx on 17.4.6.
 */

public class IndexController {
    @RequestMapping(path = {"/", "index"})
    @ResponseBody
    public String index() {
        return "Hello newCoder";
    }

    @RequestMapping("/profile/{groupId}/{userId}")
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userid,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "newcoder") String key
    ) {
        return String.format("{%s},{%d},{%d},{%s}", groupId, userid, type, key);
    }


    @RequestMapping(value = "/vm")
    public String vm(Model model) {
        model.addAttribute("par1", "par1value");


        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            map.put(String.valueOf(i), String.valueOf(i * i));
        }


        model.addAttribute("colors", colors);
        model.addAttribute("map", map);


        model.addAttribute("user", new User("Jhon"));
        return "news";
    }

    @RequestMapping(value = "/request")
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session) {

        Enumeration<String> headers = request.getHeaderNames();
        StringBuilder stringBuilder = new StringBuilder();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            stringBuilder.append(name + ":" + request.getHeader(name));
        }
        return stringBuilder.toString();
    }
}
