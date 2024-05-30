package com.homework.todo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.todo.jwt.JwtUtil;
import com.homework.todo.service.CommentService;
import com.homework.todo.service.TodoService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final String[] whiteList = new String[] {"/user/signup", "/user/login"};

    private final JwtUtil jwtUtil;
    private final CommentService commentService;
    private final TodoService todoService;
    private final FilterExceptionHandler exceptionHandler = new FilterExceptionHandler(new ObjectMapper());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (whiteListCheck(request.getRequestURI()) || request.getMethod().equals("GET")) {
                log.info("화이트리스트입니다. 필터를 통과합니다.");
                chain.doFilter(request, response);
                return;
            }

            log.info("화이트리스트가 아닙니다. 토큰 보유여부 확인");
            if (!isContainToken(request)) {
                exceptionHandler.handleException(response, HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.");
                return;
            }

            log.info("토큰 보유중");
            String token = jwtUtil.getJwtFromHeader(request);
            log.info("헤더의 토큰 가져오기");
            if (jwtUtil.validateToken(token)) {
                log.info("토큰 검증 완료");
            } else {
                exceptionHandler.handleException(response, HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.");
            }

            // 수정, 삭제 권한 확인
            if (request.getMethod().equals("PUT") || request.getMethod().equals("DELETE")) {
                String tokenUsername = jwtUtil.getUserInfoFromToken(token).getSubject();
                log.info(tokenUsername);
                // Todo 접근
                if (request.getRequestURI().equals("/todos")) {
                    String todoUsername = todoService.getTodoById(Long.valueOf(request.getParameter("id"))).getUsername();
                    log.info(todoUsername);
                    if (!tokenUsername.equals(todoUsername)) {
                        exceptionHandler.handleException(response, HttpStatus.BAD_REQUEST, "작성자만 삭제/수정할 수 있습니다.");
                        return;
                    }
                }

                // Comment 접근
                if (request.getRequestURI().startsWith("/todos/query")) {
                    String[] uriParts = request.getRequestURI().split("/");

                    Long todoId = Long.parseLong(uriParts[uriParts.length - 3]);
                    Long commentId = Long.parseLong(uriParts[uriParts.length -1]);
                    String commentUsername = commentService.findCommentUsernameById(todoId, commentId);
                    log.info(commentUsername);
                    if (!tokenUsername.equals(commentUsername)) {
                        exceptionHandler.handleException(response, HttpStatus.BAD_REQUEST, "작성자만 삭제/수정할 수 있습니다.");
                        return;
                    }
                }
            }

            chain.doFilter(request, response);
        } catch (UnsupportedJwtException | MalformedJwtException | io.jsonwebtoken.security.SignatureException e) {
            exceptionHandler.handleException(response, HttpStatus.BAD_REQUEST, "잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            exceptionHandler.handleException(response, HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
        } catch (IllegalArgumentException e) {
            exceptionHandler.handleException(response, HttpStatus.BAD_REQUEST, "JWT 토큰이 잘못되었습니다.");
        }
    }

    private boolean whiteListCheck (String uri) {
        boolean test = PatternMatchUtils.simpleMatch(whiteList, uri);
        return PatternMatchUtils.simpleMatch(whiteList, uri);
    }

    private boolean isContainToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        boolean test = authorization != null && authorization.startsWith("Bearer ");
        return authorization != null && authorization.startsWith("Bearer ");
    }
}
