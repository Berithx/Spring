package com.homework.todo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "Filter Exception Handling")
@RequiredArgsConstructor
public class FilterExceptionHandler {
    private final ObjectMapper objectMapper;

    public void handleException(HttpServletResponse response, HttpStatus status, String message) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String > errorResponse= new HashMap<>();
        errorResponse.put("error", message);
        errorResponse.put("status", String.valueOf(status));

        try {
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            log.error("Exception handling JSON 작성 오류", e);
        }
    }
}
