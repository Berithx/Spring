package com.homework.todo.controller;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * POST,  API 대응
     * List nullField 에 null 필드 정보 저장 후 Message 로 변환하여 리턴
     * RequestDtd 객체에 담긴 데이터 중 하나의 필드라도 Null인 경우 예외처리
     */
    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<String> handlePropertyValueException(PropertyValueException ex) {
        List<String> nullField = findNullFields(ex);
        String errorMessage = generateErrorMessage(nullField);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * handlePropertyValueException Method 대응
     * 발생한 Exception 객체의 null 필드를 List에 담아 null check
     */
    private List<String> findNullFields(PropertyValueException ex) {
        List<String> nullFields = new ArrayList<>();

        nullFields.add(ex.getPropertyName());
        return nullFields;
    }

    /**
     * findNullFields Method 대응
     * List 로 변환된 Null 필드 데이터를 Message 로 변환
     */
    private String generateErrorMessage(List<String> nullFields) {
        StringBuilder errorMessage = new StringBuilder("다음 필수 필드가 null입니다 : ");
        for(String field : nullFields) {
            errorMessage.append(field).append(", ");
        }
        errorMessage.delete(errorMessage.length() - 2, errorMessage.length());
        return errorMessage.toString();
    }
}