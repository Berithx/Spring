package com.homework.todo.controller;

import com.homework.todo.dto.TodoDeleteDto;
import com.homework.todo.dto.TodoRequestDto;
import com.homework.todo.dto.TodoResponseDto;
import com.homework.todo.service.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
    public TodoResponseDto createTodo(@Valid @RequestBody TodoRequestDto requestDto) {
        return todoService.createTodo(requestDto);
    }

    @GetMapping("/query")
    public TodoResponseDto getTodoById(@RequestParam @NotNull Long id) {
        return todoService.getTodoById(id);
    }

    @GetMapping
    public List<TodoResponseDto> getTodo() {
        return todoService.getTodo();
    }

    @PutMapping
    public TodoResponseDto updateTodo(@RequestParam Long id, @Valid @RequestBody TodoRequestDto requestDto) {
        return todoService.updateTodo(id, requestDto);
    }

    @DeleteMapping
    public Long deleteTodo(@RequestParam() @NotNull Long id, @Valid @RequestBody TodoDeleteDto deleteDto) {
        return todoService.deleteTodo(id, deleteDto);
    }
}
