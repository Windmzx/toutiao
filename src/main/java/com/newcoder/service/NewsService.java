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
    public List<News> getLastedNews(int id,int offset,int limit){
        return newsDAO.selectByUserIdAndOffset(id,offset,limit);
    }
}
