package com.newcoder.async.handler;

import com.newcoder.async.EventHandler;
import com.newcoder.async.EventType;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mzx on 17.4.14.
 */
@Component
public class LikeHandler implements EventHandler {
    @Override
    public void doHandle() {
        System.out.println("liked");

    }

    @Override
    public List<EventType> getSupportEnvenType() {
        return Arrays.asList(EventType.LIKE);
    }
}
