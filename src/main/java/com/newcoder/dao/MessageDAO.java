package com.newcoder.dao;

import com.newcoder.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by mzx on 17.4.10.
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = " from_id, to_id, content, created_date, has_read, conversation_id";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"INSERT INTO " + TABLE_NAME, " ( ", INSERT_FIELDS, " ) values ( #{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId} )"})
    public int addMessage(Message message);


    @Select({" SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE conversation_id=#{conversationId} limit #{offset},#{limit}"})
    public List<Message> getConversationMessage(@Param("conversationId") String conversationId,
                                                @Param("offset") int offset,
                                                @Param("limit") int limit);
    //SELECT *,COUNT(id) as cnt FROM (SELECT * FROM toutiao.message WHERE to_id=2 or from_id=2) tt GROUP BY conversation_id ORDER BY created_date	desc;

    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME, " where from_id=#{userId} or to_id=#{userId} order by id desc) tt group by conversation_id order by conversation_id desc limit #{offset},#{limit}"})
    public List<Message> getConversationList(@Param("userId") int userId,
                                             @Param("offset") int offset,
                                             @Param("limit") int limit);

    @Select({"select COUNT(id)  from ", TABLE_NAME, " where to_id=#{userId} and conversation_id=#{conversationId}"})
    public int getUnreadCount(@Param("userId") int userId,
                              @Param("conversationId") String conversationId);

}
