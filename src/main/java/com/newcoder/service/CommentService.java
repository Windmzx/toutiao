package com.newcoder.service;

import com.newcoder.dao.CommentDAO;
import com.newcoder.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mzx on 17.4.9.
 */

@Service
public class CommentService {

    @Autowired
    CommentDAO commentDao;

    public List<Comment> getCommentByTypeAndId(int id, int type) {
        return commentDao.selectbyTypeAndId(id, type);
    }

    public int getCommentConutByTypeAndId(int id, int type) {
        return commentDao.queryCountbyTypeAndId(id, type);
    }


    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }
}
