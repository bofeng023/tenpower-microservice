package com.tenpower.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import utils.JwtUtil;

/**
 * @Description 后台网关启动类
 * @Author bofeng
 * @Date 2019/4/20 9:56
 * @Version 1.0
 */
@SpringBootApplication
@EnableZuulProxy
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
