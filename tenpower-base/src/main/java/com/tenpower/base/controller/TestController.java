package com.tenpower.base.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author bofeng
 * @Date 2019/4/22 15:48
 * @Version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/test")
@RefreshScope
public class TestController {
    @Value("${my-test.domain}")
    private String domain;

    /**
     * 测试SpringCloudBus
     * @return
     */
    @GetMapping("/bus")
    public String testBus() {
        System.out.println("得到的域名是：" + domain);
        return domain;
    }
}
