package com.homework.todo.controller;

import com.homework.todo.dto.TodoRequestDto;
import com.homework.todo.dto.TodoResponseDto;
import com.homework.todo.entity.Todo;
import com.homework.todo.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todo")
    public TodoResponseDto createTodo(@RequestBody TodoRequestDto requestDto) {
        return todoService.createTodo(requestDto);
    }

    @GetMapping("/todo/query")
    public TodoResponseDto getTodoById(@RequestParam Long id) {
        return todoService.getTodoById(id);
    }

    @GetMapping("/todo")
    public List<TodoResponseDto> getTodo() {
        return todoService.getTodo();
    }

    @PutMapping("/todo/{id}")
    public Optional<TodoResponseDto> updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        return todoService.updateTodo(id, requestDto);
    }

    @DeleteMapping("/todo/{id}")
    public void deleteTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        todoService.deleteTodo(id, requestDto);
    }
}
