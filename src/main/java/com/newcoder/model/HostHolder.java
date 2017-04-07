package com.newcoder.model;

import org.springframework.stereotype.Component;

/**
 * Created by mzx on 17.4.7.
 */

@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clearUser(){
        users.remove();
    }

}
