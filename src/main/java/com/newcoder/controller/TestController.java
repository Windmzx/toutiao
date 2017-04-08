package com.newcoder.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by mzx on 17.4.8.
 */
@Controller
public class TestController  {
    @Value("${key}")
    String string;
    @RequestMapping(path = "/test")
    @ResponseBody
    public String test(){

        return string;
    }

}
