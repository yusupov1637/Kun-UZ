package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class SecuredFilterConfig {
    @Autowired
    private JwtFilter tokenFilter;

    @Bean
    public FilterRegistrationBean filterRegistration(){
        FilterRegistrationBean bean=new FilterRegistrationBean();
        bean.setFilter(tokenFilter);
        bean.addUrlPatterns("/article/type/*");
        bean.addUrlPatterns("/region/*");
        bean.addUrlPatterns("/category/*");
        bean.addUrlPatterns("/email");
        bean.addUrlPatterns("/email/date");
        bean.addUrlPatterns("/article/admin/*");
        bean.addUrlPatterns("/email/pagination");
        bean.addUrlPatterns("/attach/pagination");
        bean.addUrlPatterns("/attach/delete/*");
        bean.addUrlPatterns("/article/status");
        return bean;
    }
}
