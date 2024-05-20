package com.homework.todo.controller;

import com.homework.todo.dto.TodoRequestDto;
import com.homework.todo.dto.TodoResponseDto;
import com.homework.todo.service.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public TodoResponseDto createTodo(@RequestBody TodoRequestDto requestDto) {
        return todoService.createTodo(requestDto);
    }

    @GetMapping("/query")
    public TodoResponseDto getTodoById(@Valid @RequestParam(value = "id") @NotNull Long id) {
        return todoService.getTodoById(id);
    }

    @GetMapping
    public List<TodoResponseDto> getTodo() {
        return todoService.getTodo();
    }

    @PutMapping
    public TodoResponseDto updateTodo(@Valid @RequestParam @Size(min = 1) @NotNull Long id, @RequestBody TodoRequestDto requestDto) {
        return todoService.updateTodo(id, requestDto);
    }

    @DeleteMapping
    public void deleteTodo(@Valid @RequestParam() @Size(min = 1) @NotNull Long id, @RequestBody TodoRequestDto requestDto) {
        todoService.deleteTodo(id, requestDto);
    }
}
