package com.newcoder.dao;

import com.newcoder.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by mzx on 17.4.9.
 */

@Mapper
public interface CommentDAO {


    String TABLE_NAME = "comment";
    String INSERT_FIELDS = " content, user_id, entity_id, entity_type, created_date, status ";
    String SELECT_FIELDS = " id, content, user_id, entity_id, entity_type, created_date, status ";


    @Insert({" INSERT INTO ", TABLE_NAME, " ( ", INSERT_FIELDS, " ) VALUES ( #{content},#{userId},#{entityId},#{entityType},#{createdDate},#{status})"})
    public int addComment(Comment comment);


    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, "WHERE entity_id=#{entityId} and entity_type=#{entutyType} "})
    public List<Comment> selectbyTypeAndId(@Param("entityId") int entityId, @Param("entutyType") int entutyType);


    @Select({"SELECT count(id) ", " FROM ", TABLE_NAME, "WHERE entity_id=#{entityId} and entity_type=#{entutyType} "})
    public int queryCountbyTypeAndId(@Param("entityId") int entityId, @Param("entutyType") int entutyType);


}
