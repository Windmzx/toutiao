package com.newcoder.controller;

import com.newcoder.async.EventModel;
import com.newcoder.async.EventProducer;
import com.newcoder.async.EventType;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by mzx on 17.4.9.
 */
@SuppressWarnings("DefaultFileTemplate")
@Controller
public class CommentController {

    @Autowired
    private
    HostHolder hostHolder;

    @Autowired
    private
    CommentService commentService;

    @Autowired
    private
    NewsDAO newsDAO;

    @Autowired
    private
    NewsService newsService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = "/addComment", method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content,
                             HttpServletRequest request,
                             RedirectAttributesModelMap modelMap) {

        String res = request.getHeader("Referer");
        if (content == null || content.length() < 4) {
            //评论长度不够
            modelMap.addFlashAttribute("message", "评论长度不对");
            return "redirect:" + res;
        }
        int userid = hostHolder.getUser().getId();

        Comment comment = new Comment();
        comment.setContent(HtmlUtils.htmlEscape(content));
        comment.setStatus(1);
        comment.setEntityType(EntityType.ENTY_NEWS);
        comment.setCreatedDate(new Date());
        comment.setUserId(userid);
        comment.setEntityId(newsId);
        commentService.addComment(comment);
        News news = newsDAO.getNews(newsId);
        news.setCommentCount(news.getCommentCount() + 1);
        newsService.updateCommentCount(news);

        // 发送通知给被评论的人 1.什么事件 谁发出的 对哪一条新闻评论 对谁的评论
        EventModel eventModel = new EventModel(EventType.COMMENT);
        eventModel.setActorid(userid)
                .setEntityType(EntityType.ENTY_COMMENT)
                .setEntityId(newsId)
                .setEntityOwener(news.getUserId());
        eventProducer.fireEvent(eventModel);

        return "redirect:" + res;
    }
}
