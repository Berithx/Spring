package com.homework.todo.service;

import com.homework.todo.dto.TodoRequestDto;
import com.homework.todo.dto.TodoResponseDto;
import com.homework.todo.entity.Todo;
import com.homework.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        // RequestDto -> Entity 변환
        Todo todo = new Todo(requestDto);

        // DB 저장
        Todo saveTodo = todoRepository.save(todo);

        // Entity -> Response 변환
        TodoResponseDto todoResponseDto = new TodoResponseDto(todo);
        return todoResponseDto;
    }

    public List<TodoResponseDto> getTodoById(Long id) {
        // DB 조회
        return todoRepository.findAllById(id).stream().map(TodoResponseDto::new).toList();
    }
}
