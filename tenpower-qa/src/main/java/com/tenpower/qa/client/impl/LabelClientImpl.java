package com.tenpower.qa.client.impl;

import com.tenpower.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author bofeng
 * @Date 2019/4/18 12:23
 * @Version 1.0
 */
@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findByLabelId(String id) {
        return new Result(false, StatusCode.ERROR, "熔断器启动了");
    }
}
