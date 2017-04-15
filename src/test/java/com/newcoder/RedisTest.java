package com.newcoder;

import com.newcoder.model.User;
import com.newcoder.utils.JedisAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by mzx on 17.4.14.
 */
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {

    @Autowired
    JedisAdapter jedisAdapter;


    @Test
    public void test() {
        User user = new User();
        user.setPassword("123");
        user.setName("hahaha");
        jedisAdapter.setObject("user1", user);

        User user2;
        user2 = jedisAdapter.getObject("user1", User.class);
        System.out.println(user2);

    }
}
