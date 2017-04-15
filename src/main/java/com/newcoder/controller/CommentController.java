package com.newcoder.controller;

import com.newcoder.dao.NewsDAO;
import com.newcoder.model.Comment;
import com.newcoder.model.EntityType;
import com.newcoder.model.HostHolder;
import com.newcoder.model.News;
import com.newcoder.service.CommentService;
import com.newcoder.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by mzx on 17.4.9.
 */
@Controller
public class CommentController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    NewsDAO newsDAO;

    @Autowired
    NewsService newsService;

    @RequestMapping(path = "addComment", method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId, @RequestParam("content") String content, HttpServletRequest request) {
        String res = request.getHeader("Referer");
        System.out.println(res+"＿＿＿＿＿＿＿＿＿＿＿＿＿＿＋＋＋＋＋＋＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿");
        Comment comment = new Comment();
        comment.setStatus(1);
        comment.setEntityType(EntityType.ENTY_NEWS);
        comment.setCreatedDate(new Date());
        int userid = hostHolder.getUser().getId();
        comment.setUserId(userid);
        comment.setContent(content);
        comment.setEntityId(newsId);

        commentService.addComment(comment);

        News news = newsDAO.getNews(newsId);
        news.setCommentCount(news.getCommentCount() + 1);
        newsService.updateCommentCount(news);

        return "redirect:"+res;
    }
}
