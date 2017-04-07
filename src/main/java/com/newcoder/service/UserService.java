package com.newcoder.service;


import com.newcoder.dao.UserDAO;
import com.newcoder.model.User;
import com.newcoder.utils.ServiceUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Service
public class UserService {


    @Autowired
    private UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashedMap();

        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空");
            return map;

        }

        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
            return map;

        }
        User user = userDAO.selectByName(username);
        if (user != null) {
            map.put("msg", "用户名已经被注册");
            return map;
        }

        user = new User();
        user.setName(username);
        Random r = new Random();
        user.setHeadUrl(String.format("http://images.newcoder.com/head/%dt.png",r.nextInt(1000)));
        user.setSalt(UUID.randomUUID().toString().substring(1, 5));
        user.setPassword(ServiceUtil.MD5(password) + user.getSalt());

        userDAO.addUser(user);

        return map;
    }
}
