package com.newcoder.async.handler;

import com.newcoder.async.EventHandler;
import com.newcoder.async.EventModel;
import com.newcoder.async.EventType;
import com.newcoder.model.Message;
import com.newcoder.service.MessageService;
import com.newcoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by mzx on 17.4.14.
 */
@Component
public class LikeHandler implements EventHandler {
    //点赞
    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel eventModel) {
        int actorId = eventModel.getActorid();
        int owerId = eventModel.getEntityOwener();
        Message message = new Message();
        message.setContent("用户" + userService.getUser(actorId).getName() + "赞了你的资讯");
        message.setCreatedDate(new Date());
        message.setFromId(0);
        message.setToId(owerId);
        message.setHasRead(0);
        message.setConversationId(actorId > owerId ? String.format("%d_%d", owerId, 0) : String.format("%d_%d", 0, owerId));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEnvenType() {
        return Collections.singletonList(EventType.LIKE);
    }
}
