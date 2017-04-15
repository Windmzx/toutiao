package com.newcoder;

import com.newcoder.dao.LoginTicketDAO;
import com.newcoder.dao.MessageDAO;
import com.newcoder.dao.NewsDAO;
import com.newcoder.dao.UserDAO;
import com.newcoder.model.LoginTicket;
import com.newcoder.model.Message;
import com.newcoder.model.News;
import com.newcoder.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.Random;

/**
 * Created by mzx on 17.4.6.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@WebAppConfiguration
@Sql("/init-schema.sql")
public class InitDbTest {

    @Autowired
    UserDAO userDAO;

    @Autowired
    NewsDAO newsDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;


    @Autowired
    MessageDAO messageDAO;
    @Test
    public void contextLoads() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.newcoder.com/head/%dt.png", r.nextInt(1000)));
            user.setName("user" + i);
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            news.setCreatedDate(date);

            Random random = new Random();
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(i + 1);
            news.setUserId(i + 1);
            news.setTitle(String.format("TITLE%d", i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
            newsDAO.addNews(news);


            user.setPassword("newpassword");
            userDAO.updatepassword(user);


            LoginTicket loginTicket = new LoginTicket();
            loginTicket.setExpired(new Date());
            loginTicket.setStatus(0);
            loginTicket.setTicket(String.format("abcdefg%d", i));
            loginTicket.setUserId(i);
            loginTicketDAO.insert(loginTicket);

        }
        Message message=new Message();
        message.setContent("nihaoma");
        message.setConversationId("123");
        message.setCreatedDate(new Date());
        message.setHasRead(0);
        message.setFromId(1);
        message.setToId(3);
        messageDAO.addMessage(message);

        LoginTicket loginTicket = loginTicketDAO.selectByTicket("abcdefg2");
        loginTicket.setStatus(2);
        loginTicketDAO.upDateStatus(loginTicket.getTicket(),loginTicket.getStatus());

        Assert.assertEquals("newpassword", userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));
        Assert.assertEquals("abcdefg2",loginTicketDAO.selectByTicket("abcdefg2").getTicket());

    }
}
