package com.newcoder.service;

import com.newcoder.dao.NewsDAO;
import com.newcoder.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mzx on 17.4.6.
 */

@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;

    public News getNewsById(int newsId) {
        return newsDAO.getNews(newsId);
    }

    public List<News> getLastedNews(int id, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(id, offset, limit);
    }

    public int updateCommentCount(News news) {
        return newsDAO.updateCommentCount(news);
    }

    public int updateLikedCount(int id,int likeCount){
        return newsDAO.updateLikeCount(id, likeCount);
    }

//    public String saveFile(MultipartFile file) throws IOException {
//        //检查文件类型
//        int dopoint = file.getOriginalFilename().lastIndexOf('.');
//        if (dopoint < 0) {
//            return null;
//        }
//        String fileExt = file.getOriginalFilename().substring(dopoint + 1);
//        if (!ToutiaoUtil.isPictureAccepted(fileExt)) {
//            return null;
//        }
//        //
//        String filename = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
//        String path = ToutiaoUtil.FILE_PATH + filename;
//        Files.copy(file.getInputStream(), new File(path).toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//        return ToutiaoUtil.APP_IMG_PATH + filename;
//    }
}
