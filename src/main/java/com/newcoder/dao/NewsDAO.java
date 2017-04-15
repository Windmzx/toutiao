package com.newcoder.dao;

import com.newcoder.model.News;
import org.apache.ibatis.annotations.*;

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

    @Select({"select ", SELECT_FIELDS, " from " + TABLE_NAME + " where id=#{newsId}"})
    News getNews(int newsId);


    List<News> selectByUserIdAndOffset(@Param("userId") int userId,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit);

    @Update({"UPDATE ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(News news);

    @Update({"update ", TABLE_NAME, " set like_count = #{likeCount} where id=#{id}"})
    int updateLikeCount(@Param("id") int id, @Param("likeCount") int likeCount);

}
