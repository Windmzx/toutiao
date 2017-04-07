package com.newcoder.dao;

import com.newcoder.model.User;
import org.apache.ibatis.annotations.*;



/**
 * Created by mzx on 17.4.6.
 */
@Mapper
public interface UserDAO {

    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name ,password, salt, head_url ";
    String SELECT_FIELDS = " id, name, password, salt, head_url ";

    @Insert({"INSERT into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);


    @Update({"UPDATE ", TABLE_NAME, "set password=#{password} where id=#{id}"})
    void updatepassword(User user);

    @Select({"SELECT ", SELECT_FIELDS, " from " + TABLE_NAME + " where id=#{id}"})
    User selectById(int id);

    @Select({"SELECT ", SELECT_FIELDS, " from " + TABLE_NAME + " where name=#{name}"})
    User selectByName(String name);



    @Delete({"DELETE from " + TABLE_NAME + " where id=#{id}"})
    void deleteById(int id);
}
