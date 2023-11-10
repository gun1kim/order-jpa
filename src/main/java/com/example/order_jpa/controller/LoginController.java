package com.example.order_jpa.controller;

import com.example.order_jpa.dto.UserLoginDto;
import com.example.order_jpa.entity.User;
import com.example.order_jpa.service.LoginService;
import com.example.order_jpa.session.SessionConst;
import com.example.order_jpa.session.UserSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class LoginController {

    private final LoginService loginService;


    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    @GetMapping
    public String Home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDto userLoginDto, @RequestParam(defaultValue = "/") String redirectURI, HttpServletRequest request, HttpServletResponse response) {
        User loginUser = loginService.login(userLoginDto);
        if (loginUser == null) {

            return "redirect:/login";
        }

        // 로그인 생성 시 session 생성
        UserSession userSession = new UserSession();
        userSession.setUserId(loginUser.getUserId());
        userSession.setName(loginUser.getName());
        userSession.setEmail(loginUser.getEmail());

//        String uuid = UUID.randomUUID().toString();
        log.info("userSession = {} ", userSession.toString());
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.SESSION_NAME, userSession);


        // 로그인에 성공시 session의 id로 쿠키 설정
        Cookie cookie = new Cookie(SessionConst.COOKIE_NAME, SessionConst.SESSION_NAME);
        cookie.setMaxAge(600);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:" + redirectURI;

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie(SessionConst.COOKIE_NAME, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
