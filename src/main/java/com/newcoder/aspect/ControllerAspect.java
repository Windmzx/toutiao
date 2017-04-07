package com.newcoder.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by mzx on 17.4.7.
 */
@Aspect
@Component
public class ControllerAspect {
    private Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Before("execution(* com.newcoder.controller.HomeController.*(..) )")
    public void printParam(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for (Object o : joinPoint.getArgs()) {
            sb.append("arg:" + o.toString() + "|");
        }
        logger.info(sb.toString());

    }
}
