package com.homework.todo.controller;

import com.homework.todo.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Todo : 수정, 삭제 API
     * 비밀번호 검증 결과에서 미일치 시 예외처리
     */
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidParameterException(InvalidParameterException ex) {
        ErrorResponseDto responseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Todo : 단일 조회, 수정, 삭제 API
     * Comment : 등록, 수정, 삭제 API
     * 전달된 ID로 객체 조회 시 해당 객체가 DB에 존재하지 않을 경우 예외처리
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponseDto> handleNoSuchElementException(NoSuchElementException ex) {
        ErrorResponseDto responseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    /**
     * Todo : 작성, 수정 API
     * RequestBody 를 통해 전달된 데이터의 필수여부 유효성 검사 결과에 따른 예외처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("검증 실패: ");

        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMessage.append(error.getField()).append(error.getDefaultMessage());
        }

        ErrorResponseDto responseDto = new ErrorResponseDto(errorMessage.toString(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Todo : 단일 조회, 수정, 삭제 객체 API 대응
     * API URL 을 통해서 전달된 ID 값이 Long 타입 외 입력인 경우 예외처리
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ErrorResponseDto responseDto = new ErrorResponseDto("정의되지 않은 데이터 타입 입니다 : " + ex.getName(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    /*
     * Todo : 단일 조회, 수정, 삭제 API
     * API URL 을 통하여 전달된 파라미터 값이 validation 을 만족하지 못한 경우 예외처리
     */
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(jakarta.validation.ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            String parameterName = violation.getPropertyPath().toString();
            errorMessage.append(parameterName).append(": ").append(violation.getMessage());
        }

        ErrorResponseDto responseDto = new ErrorResponseDto(errorMessage.toString(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Todo : 단일 조회, 수정, 삭제 객체 API
     * PathVariable 을 통하여 전달된 ID 값이 null 인 경우 예외처리
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingPathVariableException(MissingPathVariableException ex) {
        ErrorResponseDto responseDto = new ErrorResponseDto("요청 URL에 "+ ex.getParameter().getParameterName() + "이(가) 입력되지 않았습니다. 확인 후 재요청해주시기 바랍니다.", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Todo : 단일 조회, 수정, 삭제 객체 API
     * PathVariable 을 통하여 전달된 ID 값이 null 인 경우 예외처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ErrorResponseDto responseDto = new ErrorResponseDto("요청 URL에 "+ ex.getParameterName() + "이(가) 입력되지 않았습니다. 확인 후 재요청해주시기 바랍니다.", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Null 입력에 대한 대응
     * 현재 적용위치 : commentController
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDto> hangleNullPointerException(NullPointerException ex) {
        ErrorResponseDto responseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    /**
     *
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponseDto responseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}