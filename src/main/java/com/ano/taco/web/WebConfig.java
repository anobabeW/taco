package com.ano.taco.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wangjiao
 * @version 1.0
 * @date 2021/12/28 13:59
 * 替代HomeController
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 也可以添加在TacoApplication启动类中
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }
}
