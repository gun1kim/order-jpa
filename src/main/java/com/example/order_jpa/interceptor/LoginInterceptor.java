package com.example.order_jpa.interceptor;

import com.example.order_jpa.session.SessionConst;
import com.example.order_jpa.session.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String redirectURI = request.getRequestURI();
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login?redirectURI=" + redirectURI);
            return false;
        } else {
            UserSession userSession = (UserSession)session.getAttribute(SessionConst.SESSION_NAME);
            log.info("userSession: {}", userSession);
            if (userSession == null) {
                response.sendRedirect(request.getContextPath() + "/login?redirectURI=" + redirectURI);
                return false;
            }
        }
        return true;
    }

}
