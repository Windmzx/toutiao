package com.newcoder.async;

import com.newcoder.service.LikedService;

import java.util.List;

/**
 * Created by mzx on 17.4.14.
 */
public interface EventHandler {
    void doHandle();

    List<EventType> getSupportEnvenType();
}
