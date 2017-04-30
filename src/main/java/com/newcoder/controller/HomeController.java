package com.newcoder.controller;

import com.newcoder.model.*;
import com.newcoder.service.LikedService;
import com.newcoder.service.NewsService;
import com.newcoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzx on 17.4.6.
 */
@Controller
public class HomeController {
    @Autowired
    private
    NewsService newsService;

    @Autowired
    private
    UserService userService;

    @Autowired
    private
    LikedService likedService;

    @Autowired
    private
    HostHolder hostHolder;
    String key;

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> news = newsService.getLastedNews(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();

        /*
        vo.like表示用户是否喜欢
        vo.news.like是真正点赞的人
         */
        User curruser = hostHolder.getUser();
        for (News news1 : news) {
            User user = userService.getUser(news1.getUserId());
            ViewObject vo = new ViewObject();
            vo.set("news", news1);
            vo.set("user", userService.getUser(news1.getUserId()));

            //登陆的情况下才会显示当前用户的点赞情况
            if (curruser != null) {
                int islike = likedService.getLikedStatus(curruser.getId(), EntityType.ENTY_NEWS, news1.getId());
                vo.set("like", islike);
            }
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
