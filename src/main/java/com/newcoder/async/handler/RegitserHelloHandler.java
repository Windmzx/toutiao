package com.newcoder.async.handler;

import com.newcoder.async.EventHandler;
import com.newcoder.async.EventModel;
import com.newcoder.async.EventType;
import com.newcoder.model.Message;
import com.newcoder.service.MessageService;
import com.newcoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by mzx on 4/18/2017.
 */
@Component
public class RegitserHelloHandler implements EventHandler {

    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel eventModel) {
        //注册的话系统发送欢迎
        int actorId = eventModel.getActorid();
        int owerId = eventModel.getEntityOwener();
        Message message = new Message();
        message.setContent("欢迎你注册本网站");
        message.setCreatedDate(new Date());
        message.setFromId(0);
        message.setToId(owerId);
        message.setHasRead(0);
        message.setConversationId(String.format("%d_%d", 0, owerId));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEnvenType() {
        return Arrays.asList(EventType.REGISTER);
    }
}
