package com.homework.todo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.todo.dto.LoginRequestDto;
import com.homework.todo.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Slf4j(topic = "로그인 시도 객체 검증")
@RequiredArgsConstructor
public class VerifyUserFilter implements Filter {
    public static final String VERIFY_USER = "verifyUser";

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final FilterExceptionHandler exceptionHandler = new FilterExceptionHandler(new ObjectMapper());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (httpServletRequest.getMethod().equals("POST") && httpServletRequest.getRequestURI().equals("/user/login")) {
            try {
                log.info("로그인 시도 객체 검증 시작");
                LoginRequestDto loginRequestDto = objectMapper.readValue(request.getReader(), LoginRequestDto.class);
                log.info("Dto 객체 생성");

                if(userService.verifyUser(loginRequestDto)) {
                    request.setAttribute(VERIFY_USER, loginRequestDto);
                    log.info("사용자 인증 성공", loginRequestDto.getUsername());
                } else {
                    log.error("사용자 인증 실패", loginRequestDto.getUsername());
                    exceptionHandler.handleException((HttpServletResponse) response, HttpStatus.UNAUTHORIZED, "회원을 찾을 수 없습니다.");
                    return;
                }
                chain.doFilter(request, response);
            } catch (IOException e) {
                log.error("사용자 조회 실패");
                exceptionHandler.handleException((HttpServletResponse) response, HttpStatus.BAD_REQUEST, "사용자 조회 실패");
            } catch (IllegalArgumentException e) {
                exceptionHandler.handleException((HttpServletResponse) response, HttpStatus.UNAUTHORIZED, "회원을 찾을 수 없습니다.");

            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
