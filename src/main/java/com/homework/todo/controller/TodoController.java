package com.homework.todo.controller;

import com.homework.todo.dto.TodoRequestDto;
import com.homework.todo.dto.TodoResponseDto;
import com.homework.todo.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @GetMapping("/todo/date")
//    public List<TodoResponseDto> getTodoByDate(@RequestParam String date) {
//        return todoService.getTodoByDate(date);
//    }

}