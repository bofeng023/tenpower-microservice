package com.tenpower.user.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author bofeng
 * @Date 2019/4/10 17:43
 * @Version 1.0
 */
@Component
public class JwtFilter implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过过滤器");
        String token = request.getHeader("Authorization");
        if (token != null) {
            try {
                Claims claims = jwtUtil.parseJWT(token);
                String roles = (String) claims.get("roles");
                if ("admin".equals(roles)) {
                    request.setAttribute("claims_admin", claims);
                } else if ("user".equals(roles)) {
                    request.setAttribute("claims_user", claims);
                }
            } catch (Exception e) {
                throw new RuntimeException("令牌不正确");
            }
        }
        return true;
    }
}
