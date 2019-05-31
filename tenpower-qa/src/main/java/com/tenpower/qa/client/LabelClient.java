package com.tenpower.qa.client;

import com.tenpower.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 微服务调用接口
 */
@FeignClient(value = "tenpower-base", fallback = LabelClientImpl.class)   //要调用的服务名
public interface LabelClient {
    @GetMapping("/label/{LabelId}")
    Result findByLabelId(@PathVariable("LabelId") String id);
}
