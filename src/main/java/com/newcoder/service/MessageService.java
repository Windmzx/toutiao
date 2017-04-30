package com.newcoder.service;

import com.newcoder.dao.MessageDAO;
import com.newcoder.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mzx on 17.4.10.
 */
@Service
public class MessageService {
    @Autowired
    private
    MessageDAO messageDAO;


    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    public List<Message> getMessageOfConversation(String conversationId, int offet, int limit) {
        return messageDAO.getConversationMessage(conversationId, offet, limit);
    }

    public List<Message> getConversationList(int userId, int offet, int limit) {
        return messageDAO.getConversationList(userId, offet, limit);
    }

    public int getUnreadMessage(int userid, String conversationId) {
        return messageDAO.getUnreadCount(userid, conversationId);
    }
}
