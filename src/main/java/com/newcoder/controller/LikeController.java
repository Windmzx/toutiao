package com.newcoder.controller;

import com.newcoder.async.EventModel;
import com.newcoder.async.EventProducer;
import com.newcoder.async.EventType;
import com.newcoder.model.EntityType;
import com.newcoder.model.HostHolder;
import com.newcoder.model.News;
import com.newcoder.service.LikedService;
import com.newcoder.service.NewsService;
import com.newcoder.utils.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mzx on 17.4.14.
 */
@Controller
public class LikeController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikedService likedService;

    @Autowired
    NewsService newsService;

    @Autowired
    EventProducer eventProducer;


    @RequestMapping(path = {"/like"},method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId) {
        int userId = hostHolder.getUser().getId();
        News news = newsService.getNewsById(newsId);
        long likeCount = likedService.like(userId, EntityType.ENTY_NEWS, newsId);
        newsService.updateLikedCount(newsId, (int) likeCount);
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setEntityOwener(news.getUserId())
                .setActorid(hostHolder.getUser().getId()).setEntityId(newsId));
        System.out.println("产生了一条消息");
        return ToutiaoUtil.getJson(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = "/dislike",method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("newsId") int newsId) {
        int userId = hostHolder.getUser().getId();
        long likeCount = likedService.dislike(userId, EntityType.ENTY_NEWS, newsId);
        newsService.updateLikedCount(newsId, (int) likeCount);
        return ToutiaoUtil.getJson(0, String.valueOf(likeCount));
    }
}
