package com.newcoder.configuration;

import com.newcoder.intercepter.LoginRequiredIntercepter;
import com.newcoder.intercepter.PassportIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * Created by mzx on 17.4.7.
 */

@Component
public class ToutiaoWebConfiguration extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Autowired
    private
    PassportIntercepter passportIntercepter;


    @Autowired
    private
    LoginRequiredIntercepter loginRequiredIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportIntercepter);
        registry.addInterceptor(loginRequiredIntercepter)
                .addPathPatterns("/setting",
                        "/addComment",
                        "/like",
                        "/dislike",
                        "/getMessage",
                        "/getConversationList",
                        "/addMessage",
                        "/addNews");
        super.addInterceptors(registry);
    }
}
