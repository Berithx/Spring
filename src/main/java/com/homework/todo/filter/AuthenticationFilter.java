package com.homework.todo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.todo.dto.LoginRequestDto;
import com.homework.todo.entity.User;
import com.homework.todo.jwt.JwtUtil;
import com.homework.todo.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final FilterExceptionHandler exceptionHandler = new FilterExceptionHandler(new ObjectMapper());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Object attribute = httpServletRequest.getAttribute(VerifyUserFilter.VERIFY_USER);

        try {
            if (attribute instanceof LoginRequestDto requestDto) {
                User user = userService.findByUsername(requestDto.getUsername());

                if (user != null && user.checkPassword(requestDto.getPassword())) {
                    String token = jwtUtil.createToken(user.getUsername(), user.getRole());
                    log.info("토큰 생성");

                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
                    log.info("토큰 헤더 주입");
                } else {
                    log.info("유효하지 않은 사용자 정보입니다.");
                    exceptionHandler.handleException((HttpServletResponse) response, HttpStatus.BAD_REQUEST, "유효하지 않은 사용자 정보입니다.");
                }
            }
            chain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            log.error("로그인 시도 중 오류 발생", e);
            exceptionHandler.handleException((HttpServletResponse) response, HttpStatus.BAD_REQUEST, "로그인 시도 중 오류가 발생했습니다.");
        }
    }
}
