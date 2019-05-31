package com.tenpower.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 后台网关过滤器
 * @Author bofeng
 * @Date 2019/4/20 12:28
 * @Version 1.0
 */
@Component
public class ManagerFilter extends ZuulFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台Zuul过滤器");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getHeader("Authorization");
        //Zuul自带OPTIONS方法，作用是根据名称映射对应的微服务。此处不应拦截该方法
        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }
        String url = request.getRequestURL().toString();
        if (url.contains("/admin/login")) {
            return null;
        }
        if (StringUtils.isNotBlank(token)) {
            try {
                Claims claims = jwtUtil.parseJWT(token);
                if ("admin".equals(claims.get("roles"))) {
                    context.addZuulRequestHeader("Authorization", token);
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                context.setSendZuulResponse(false);
            }
        }
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(403);
        context.setResponseBody("无权访问");
        context.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
