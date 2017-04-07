package com.newcoder.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by mzx on 17.4.6.
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger loger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.newcoder.controller.IndexController.* (..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb=new StringBuilder();
        for (Object o:joinPoint.getArgs()){
            sb.append("arg:"+o.toString()+"|");
        }
        loger.info(sb.toString()+"\n"+"---------------------------------------------------------------------------------");
    }

    @After("execution(* com.newcoder.controller.IndexController.* (..))")
    public void AfterMethod(JoinPoint joinPoint) {
        loger.info("After Method");
    }
}
