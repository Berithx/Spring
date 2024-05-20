package com.homework.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 수정, 삭제 API 대응
     * 비밀번호 검증 결과에서 미일치 시 예외처리
     */
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<String> handleInvalidParameterException(InvalidParameterException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * 단일 조회, 수정, 삭제 API 대응
     * RequestParam 을 통하여 전달된 ID로 객체 조회 시 해당 객체가 DB에 존재하지 않을 경우 예외처리
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 작성, 수정 API 대응
    // RequestBody를 통해 전달된 데이터의 필수여부 유효성 검사 결과에 따른 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            String validKeyName = error.getField();
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return new ResponseEntity<>(validatorResult, HttpStatus.BAD_REQUEST);
    }

    /**
     * 단일 조회, 수정, 삭제 객체 API 대응
     * RequestParam 을 통하여 전달된 ID 값이 Long 타입 외 입력인 경우 예외처리
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("입력된 '%s'의 값 '%s'은(는) 정수가 아닙니다. 정수로 입력해주시기 바랍니다..", ex.getName(), ex.getValue());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * 단일 조회, 수정, 삭제 객체 API 대응
     * RequestParam 을 통하여 전달된 ID 값이 null 인 경우 예외처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(ex.getParameterName() + "에 공백이 입력되었습니다. 확인 후 재요청해주시기 바랍니다.", HttpStatus.BAD_REQUEST);
    }
}