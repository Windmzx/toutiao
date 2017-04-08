package com.newcoder.controller;

import com.newcoder.model.News;
import com.newcoder.model.User;
import com.newcoder.model.ViewObject;
import com.newcoder.service.NewsService;
import com.newcoder.service.UserService;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzx on 17.4.6.
 */
@Controller
public class HomeController {
    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    String key;

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> news = newsService.getLastedNews(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();

        for (News news1 : news) {
            User user = userService.getUser(news1.getUserId());
            ViewObject vo = new ViewObject();
            vo.set("news", news1);
            vo.set("user", userService.getUser(news1.getUserId()));

            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = "/user/{userid}")
    public String userIndex(Model model, @PathVariable("userid") int userid) {
        model.addAttribute("vos", getNews(userid, 0, 10));

        return "home";
    }

    @RequestMapping(path = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model, @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("pop", pop);
        model.addAttribute("vos", getNews(0, 0, 10));
        return "home";
    }


}
