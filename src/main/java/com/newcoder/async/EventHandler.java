package com.newcoder.async;

import java.util.List;

/**
 * Created by mzx on 17.4.14.
 */
public interface EventHandler {
    void doHandle(EventModel eventModel);

    List<EventType> getSupportEnvenType();
}
