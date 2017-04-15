package com.newcoder.controller;

import com.newcoder.dao.NewsDAO;
import com.newcoder.model.*;
import com.newcoder.service.*;
import com.newcoder.utils.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by mzx on 17.4.8.
 */
@Controller
public class NewsController {

    @Autowired
    SaveImageToQiniu saveFile;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    NewsService newsService;

    @Autowired
    NewsDAO newsDAO;

    @Autowired
    UserService userservice;

    @Autowired
    LikedService likedService;
    @Autowired
    CommentService commentService;

    @RequestMapping(path = "/news/{newsId}", method = {RequestMethod.POST, RequestMethod.GET})
    public String getNews(@PathVariable("newsId") int newsId, Model model) {
        //获取资讯
        News news = newsService.getNewsById(newsId);
        User user = userservice.getUser(news.getUserId());
        model.addAttribute("owner", user);
        model.addAttribute("news", news);

        //登陆的情况下才会显示当前用户的点赞情况
        User curruser = hostHolder.getUser();
        if (curruser != null) {
            int islike = likedService.getLikedStatus(curruser.getId(), EntityType.ENTY_NEWS, news.getId());
            model.addAttribute("like", islike);
        }


        //获取评论
        List<Comment> ls = commentService.getCommentByTypeAndId(newsId, EntityType.ENTY_NEWS);
        List<ViewObject> ovs = new ArrayList<>();

        for (Comment comment : ls) {
            ViewObject ov = new ViewObject();
            ov.set("comment", comment);
            ov.set("user", userservice.getUser(comment.getUserId()));

            ovs.add(ov);
        }
        model.addAttribute("comments", ovs);
        return "detail";
    }


    @ResponseBody
    @RequestMapping(path = "/uploadImage", method = {RequestMethod.POST})
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        int dopoint = file.getOriginalFilename().lastIndexOf('.');
        if (dopoint < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dopoint + 1);
        if (!ToutiaoUtil.isPictureAccepted(fileExt)) {
            return null;
        }
        //
        String filename = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;

        String s = saveFile.sace(file, filename);
        if (s == null) {
            ToutiaoUtil.getJson(1, "图片上传失败 请检查你的文件类型");
        }
        return ToutiaoUtil.getJson(0, ToutiaoUtil.QINIU_DOMAMIN + s);


    }

    @ResponseBody
    @RequestMapping(path = "/addNews", method = {RequestMethod.POST})
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link) {

        News news = new News();
        news.setCreatedDate(new Date());
        news.setImage(image);
        news.setUserId(hostHolder.getUser().getId());
        news.setTitle(title);
        news.setLink(link);
        news.setCommentCount(0);
        news.setLikeCount(0);
        newsDAO.addNews(news);
        return ToutiaoUtil.getJson(0);
    }

    @RequestMapping(path = "/images", method = {RequestMethod.GET})
    @ResponseBody
    public void showinage(@RequestParam("name") String filename, HttpServletResponse response) {
        response.setContentType("image");

        try {
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.FILE_PATH + filename)), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
