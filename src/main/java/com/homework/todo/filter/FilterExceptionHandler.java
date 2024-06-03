package com.homework.todo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.todo.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@Slf4j(topic = "Filter Exception Handling")
@RequiredArgsConstructor
public class FilterExceptionHandler {
    private final ObjectMapper objectMapper;

    public void handleException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        ResponseEntity<ErrorResponseDto> responseDto = createErrorResponse(status, message);
        response.setStatus(responseDto.getStatusCode().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseDto.getBody()));
    }

    public ResponseEntity<ErrorResponseDto> createErrorResponse(HttpStatus status, String message) {
        ErrorResponseDto responseDto = new ErrorResponseDto(message, status.value());
        return new ResponseEntity<>(responseDto, status);
    }
}
