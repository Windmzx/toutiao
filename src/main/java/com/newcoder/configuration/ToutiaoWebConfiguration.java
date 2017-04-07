package com.newcoder.configuration;

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
    PassportIntercepter passportIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportIntercepter);
        super.addInterceptors(registry);
    }
}
