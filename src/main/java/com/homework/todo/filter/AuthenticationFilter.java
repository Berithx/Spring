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
import java.util.NoSuchElementException;

@Slf4j(topic = "로그인 및 JWT 생성")
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FilterExceptionHandler exceptionHandler = new FilterExceptionHandler(new ObjectMapper());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (httpServletRequest.getMethod().equals("POST") && httpServletRequest.getRequestURI().equals("/user/login")) {
            try {
                log.info("로그인 시도 객체 검증 시작");
                LoginRequestDto loginRequestDto = objectMapper.readValue(request.getReader(), LoginRequestDto.class);
                log.info("Dto 객체 생성");

                User user = userService.findByUsername(loginRequestDto.getUsername());

                if (user != null && user.checkPassword(loginRequestDto.getPassword())) {
                    String token = jwtUtil.createToken(user.getUsername(), user.getRole());
                    log.info("토큰 생성");

                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
                    log.info("토큰 헤더 주입");

                    chain.doFilter(request, response);
                } else {
                    log.error("유효하지 않은 사용자 정보입니다.");
                    exceptionHandler.handleException(httpServletResponse, HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자 정보입니다.");
                }
            } catch (IOException e) {
                log.error("사용자 조회 실패");
                exceptionHandler.handleException(httpServletResponse, HttpStatus.BAD_REQUEST, "사용자 조회 실패");
            } catch (NoSuchElementException e) {
                exceptionHandler.handleException(httpServletResponse, HttpStatus.UNAUTHORIZED, "회원을 찾을 수 없습니다.");
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
