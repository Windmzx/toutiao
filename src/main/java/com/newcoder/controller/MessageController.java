package com.newcoder.controller;

import com.newcoder.model.HostHolder;
import com.newcoder.model.Message;
import com.newcoder.model.User;
import com.newcoder.model.ViewObject;
import com.newcoder.service.MessageService;
import com.newcoder.service.UserService;
import com.newcoder.utils.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mzx on 17.4.10.
 */
@Controller
public class MessageController {
    @Autowired
    private
    UserService userService;

    @Autowired
    private
    HostHolder hostHolder;
    @Autowired
    private
    MessageService messageService;

    @RequestMapping(path = {"/getMessage"}, method = {RequestMethod.GET})
    public String getMessage(Model model, @RequestParam("conversationId") String consersation) {

        List<Message> messages = messageService.getMessageOfConversation(consersation, 0, 10);

        List<ViewObject> vos = new ArrayList<>();

        for (Message message : messages) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("message", message);
            User user = new User();
            user = userService.getUser(message.getFromId());
            viewObject.set("user", user);
            vos.add(viewObject);
        }
        model.addAttribute("messages", vos);
        return "letterDetail";

    }

    @RequestMapping(path = {"/getConversationList"}, method = {RequestMethod.GET})
    public String getMessage(Model model) {
        int userId = hostHolder.getUser().getId();
        int targetUserId;
        List<ViewObject> vos = new ArrayList<>();
        List<Message> conversations = messageService.getConversationList(userId, 0, 10);
        for (Message s : conversations) {
            ViewObject ov = new ViewObject();
            ov.set("conversation", s);
            if (s.getFromId() == userId) {
                targetUserId = s.getToId();
            } else {
                targetUserId = s.getFromId();
            }
            User user = userService.getUser(targetUserId);
            ov.set("unreadCount", messageService.getUnreadMessage(userId, s.getConversationId()));
            ov.set("userId", targetUserId);
            ov.set("userName", user.getName());
            ov.set("headUrl", user.getHeadUrl());
            vos.add(ov);
        }

        model.addAttribute("conversations", vos);
        return "letter";


    }


    @ResponseBody
    @RequestMapping(path = "/addMessage", method = {RequestMethod.POST})
    public String addMessage(@RequestParam("toId") int toid,
                             @RequestParam("content") String content) {
        int fromId = 0;
        if (hostHolder.getUser() != null) {
            fromId = hostHolder.getUser().getId();
        }
        Message message = new Message();
        message.setToId(toid);
        message.setFromId(fromId);

        message.setContent(HtmlUtils.htmlEscape(content));

        message.setConversationId(fromId > toid ? String.format("%d_%d", fromId, toid) : String.format("%d_%d", toid, fromId));
        message.setCreatedDate(new Date());
        try {
            messageService.addMessage(message);
            return ToutiaoUtil.getJson(0);
        } catch (Exception e) {
            return ToutiaoUtil.getJson(1, "发送消息失败");
        }
    }
}
