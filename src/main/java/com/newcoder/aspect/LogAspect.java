package com.newcoder.aspect;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mzx on 17.4.6.
 */
//@Aspect
//@Component
class LogAspect {

    private static final Logger loger = LoggerFactory.getLogger(LogAspect.class);

    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb=new StringBuilder();
        for (Object o:joinPoint.getArgs()){
            sb.append("arg:").append(o.toString()).append("|");
        }
        loger.info(sb.toString()+"\n"+"---------------------------------------------------------------------------------");
    }

    public void AfterMethod(JoinPoint joinPoint) {
        loger.info("After Method");
    }
}
