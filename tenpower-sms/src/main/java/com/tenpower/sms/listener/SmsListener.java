package com.tenpower.sms.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description mq监听类
 * @Author bofeng
 * @Date 2019/4/8 21:50
 * @Version 1.0
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    /**
     * 发送短信
     * @param map
     */
    @RabbitHandler
    public void sendSms(Map<String, String> map) {
        //TODO 使用短信平台发短信
        System.out.println("手机号：" + map.get("mobile"));
        System.out.println("验证码：" + map.get("code"));
    }
}
