package com.newcoder.async;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.utils.JedisAdapter;
import com.newcoder.utils.RedisKeyUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mzx on 17.4.14.
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    @Autowired
    private
    JedisAdapter jedisAdapter;
    private ApplicationContext applicationContext;
    private final Map<EventType, List<EventHandler>> eventHandlerRouter = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        //找出所有注解注册的handler
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                //每个EventHandler支持的EventType
                /**
                 * 把EventHandler-->EventType的关系转换为EventType-->EventHandler的关系
                 * 根据对应的type调用能支持这个type的handler进行处理
                 */
                List<EventType> eventTypes = entry.getValue().getSupportEnvenType();
                for (EventType type : eventTypes) {
                    if (!eventHandlerRouter.containsKey(type)) {
                        eventHandlerRouter.put(type, new ArrayList<>());
                    }

                    eventHandlerRouter.get(type).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(() -> {
            //一直运行
            while (true) {
                String key = RedisKeyUtil.getEventQueueKey();

                List<String> events = jedisAdapter.brpop(0, key);

                for (String message : events) {
                    if (message.equals(key)) {
                        continue;
                    }
                    EventModel eventModel = JSONObject.parseObject(message, EventModel.class);
                    if (eventHandlerRouter.containsKey(eventModel.getType())) {
                        for (EventHandler eventHandler : eventHandlerRouter.get(eventModel.getType())) {
                            eventHandler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
