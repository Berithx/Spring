package com.homework.todo.controller;

import com.homework.todo.dto.TodoRequestDto;
import com.homework.todo.dto.TodoResponseDto;
import com.homework.todo.dto.ValidationGroups;
import com.homework.todo.service.TodoService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/todos")
@Validated
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public TodoResponseDto createTodo(@Validated(ValidationGroups.Create.class) @RequestBody TodoRequestDto requestDto) {
        return todoService.createTodo(requestDto);
    }

    @GetMapping("/query")
    public TodoResponseDto getTodoById(@Validated @RequestParam @Positive Long id) {
        return todoService.getTodoById(id);
    }

    @GetMapping
    public List<TodoResponseDto> getTodo() {
        return todoService.getTodo();
    }

    @PutMapping
    public TodoResponseDto updateTodo(@Validated @RequestParam @Positive Long id, @Validated(ValidationGroups.Update.class) @RequestBody TodoRequestDto requestDto) {
        return todoService.updateTodo(id, requestDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTodo(@Validated @RequestParam @Positive Long id, @Validated(ValidationGroups.Delete.class) @RequestBody TodoRequestDto requestDto) {
        todoService.deleteTodo(id, requestDto);
        return ResponseEntity.ok("정상 삭제 처리되었습니다.");
    }
}
