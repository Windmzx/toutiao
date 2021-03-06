package com.newcoder.service;


import com.newcoder.async.EventModel;
import com.newcoder.async.EventProducer;
import com.newcoder.async.EventType;
import com.newcoder.dao.LoginTicketDAO;
import com.newcoder.dao.UserDAO;
import com.newcoder.model.LoginTicket;
import com.newcoder.model.User;
import com.newcoder.utils.ToutiaoUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Service
public class UserService {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private
    LoginTicketDAO loginTicketDAO;

    @Autowired
    EventProducer eventProducer;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public Map<String, Object> register(String username, String password, int rember) {
        Map<String, Object> map = new HashMap();

        if (StringUtils.isEmpty(username)) {
            map.put("msgname", "用户名不能为空");
            return map;

        }

        if (StringUtils.isEmpty(password)) {
            map.put("msgpwd", "密码不能为空");
            return map;

        }
        User user = userDAO.selectByName(username);
        if (user != null) {
            map.put("msgname", "用户名已经被注册");
            return map;
        }

        user = new User();
        user.setName(username);
        Random r = new Random();
        user.setHeadUrl(String.format("http://images.newcoder.com/head/%dt.png", r.nextInt(1000)));
        user.setSalt(UUID.randomUUID().toString().substring(1, 5));
        user.setPassword(ToutiaoUtil.MD5(password + user.getSalt()));

        userDAO.addUser(user);

        EventModel eventModel = new EventModel(EventType.REGISTER);
        eventModel.setEntityOwener(user.getId());
        eventProducer.fireEvent(eventModel);
        Date date = new Date();
        if (rember > 0) {
            date.setTime(date.getTime() + 1000 * 36000 * 24 * 5);
        } else {
            //不记住密码则只有一天的有效期
            date.setTime(date.getTime() + 1000 * 36000 * 24 * 1);
        }

        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(user.getId());
        ticket.setExpired(date);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        ticket.setStatus(0);
        loginTicketDAO.insert(ticket);

        map.put("ticket", ticket.getTicket());
        return map;
    }


    public Map<String, Object> login(String username, String password, int rember) {
        Map<String, Object> map = new HashedMap();

        if (StringUtils.isEmpty(username)) {
            map.put("msgname", "用户名不能为空");
            return map;

        }

        if (StringUtils.isEmpty(password)) {
            map.put("msgpwd", "密码不能为空");
            return map;

        }
        User user = userDAO.selectByName(username);
        //查询不到用户提供的用户名 为了保证安全,统一返回不正确
        if (user == null) {
            map.put("msgpwd", "用户名密码不正确");
            return map;
        }
        //验证密码
        if (Objects.equals(ToutiaoUtil.MD5(password + user.getSalt()), user.getPassword())) {
            //密码正确 生成ticket


            Date date = new Date();
            if (rember > 0) {
                date.setTime(date.getTime() + 1000 * 36000 * 24 * 5);
            }
            date.setTime(date.getTime() + 1000 * 36000 * 24 * 1);
            LoginTicket ticket = new LoginTicket();
            ticket.setUserId(user.getId());
            ticket.setExpired(date);
            ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
            ticket.setStatus(0);
            loginTicketDAO.insert(ticket);

            map.put("ticket", ticket.getTicket());
        } else {
            map.put("msgname", "用户名密码不正确");
        }

        return map;
    }

    public void logout(String ticket) {
        loginTicketDAO.upDateStatus(ticket, 1);
    }
}
