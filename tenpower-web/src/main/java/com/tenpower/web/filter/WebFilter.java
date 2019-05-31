package com.tenpower.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author bofeng
 * @Date 2019/4/20 10:45
 * @Version 1.0
 */
@Component  //需要加入spring容器
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";   //前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0;   //优先级为0，最高
    }

    @Override
    public boolean shouldFilter() {
        return true;   //是否执行过滤器
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("经过前台Zuul过滤器");
        RequestContext requestContext = RequestContext.getCurrentContext();
        String header = requestContext.getRequest().getHeader("Authorization");
        if (StringUtils.isNotBlank(header)) {
            requestContext.addZuulRequestHeader("Authorization", header);
        }
        return null;
    }
}
