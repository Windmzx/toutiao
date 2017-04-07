package com.newcoder.dao;

import com.newcoder.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * Created by mzx on 17.4.7.
 */
@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id, ticket, expired, status";
    String SELECT_FIELDS = " id, user_id, ticket, expired, status";

    @Insert({"INSERT into " + TABLE_NAME + " (" + INSERT_FIELDS + ") values ( #{userId},#{ticket},#{expired},#{status})"})
    void insert(LoginTicket loginTicket);

    @Select({"SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " where ticket=#{ticket} "})
    LoginTicket selectByTicket(String ticket);

    @Update({"UPDATE " + TABLE_NAME + " set status=#{status} where ticket=#{ticket}"})
    void upDateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
