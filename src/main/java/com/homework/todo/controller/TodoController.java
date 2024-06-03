package com.homework.todo.controller;

import com.homework.todo.dto.TodoRequestDto;
import com.homework.todo.dto.TodoResponseDto;
import com.homework.todo.dto.ValidationGroups;
import com.homework.todo.jwt.JwtUtil;
import com.homework.todo.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
@Validated
public class TodoController {
    private final TodoService todoService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@Validated(ValidationGroups.Create.class) @RequestBody TodoRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        TodoResponseDto responseDto = todoService.createTodo(requestDto, token);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping(value = {"/query/{id}", "/query", "/query/"})
    public ResponseEntity<TodoResponseDto> getTodoById(@Validated @PathVariable @Positive Long id) {
        TodoResponseDto responseDto = todoService.getTodoById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getTodo() {
        List<TodoResponseDto> responseList = todoService.getTodo();
        return ResponseEntity.ok(responseList);
    }

    @PutMapping
    public ResponseEntity<TodoResponseDto> updateTodo(@Validated @RequestParam @Positive Long id, @Validated(ValidationGroups.Update.class) @RequestBody TodoRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        TodoResponseDto responseDto = todoService.updateTodo(id, requestDto, token);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTodo(@Validated @RequestParam @Positive Long id, @Validated(ValidationGroups.Delete.class) @RequestBody TodoRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        todoService.deleteTodo(id, token);
        return ResponseEntity.ok("정상 삭제 처리되었습니다.");
    }
}
