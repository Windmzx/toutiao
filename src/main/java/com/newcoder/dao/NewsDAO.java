package com.newcoder.dao;

import com.newcoder.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mzx on 17.4.6.
 */
@Mapper
public interface NewsDAO {

    String TABLE_NAME = "news";
    String INSERT_FIELDS = " title, link, image, like_count, comment_count, created_date, user_id ";
    String SELECT_FIELDS = " id, title, link, image, like_count, comment_count, created_date, user_id ";

    @Insert({"INSERT into ", TABLE_NAME + " (" + INSERT_FIELDS + ") values ( #{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);


    List<News> selectByUserIdAndOffset(@Param("userId") int userId,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit);

}
