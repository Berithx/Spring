package com.homework.todo.service;

import com.homework.todo.dto.TodoDeleteDto;
import com.homework.todo.dto.TodoRequestDto;
import com.homework.todo.dto.TodoResponseDto;
import com.homework.todo.entity.Todo;
import com.homework.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional(readOnly = false)
    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        Todo todo = new Todo(requestDto);

        Todo saveTodo = todoRepository.save(todo);

        TodoResponseDto todoResponseDto = new TodoResponseDto(todo);
        return todoResponseDto;
    }

    @Transactional(readOnly = true)
    public TodoResponseDto getTodoById(Long id) {
        Todo todo = findTodoById(id);
        return new TodoResponseDto(todo);
    }

    @Transactional(readOnly = true)
    public List<TodoResponseDto> getTodo() {
        return todoRepository.findAllByOrderByCreatedAtDesc().stream().map(TodoResponseDto::new).toList();
    }

    @Transactional(readOnly = false)
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto) {
        Todo todo = findTodoById(id);
        todo.checkPassword(requestDto.getPassword());
        todo.update(requestDto);
        TodoResponseDto responseDto = new TodoResponseDto(todo);
        return responseDto;
    }

    @Transactional(readOnly = false)
    public Long deleteTodo(Long id, TodoDeleteDto deleteDto) {
        Todo todo = findTodoById(id);
        todo.checkPassword(deleteDto.getPassword());
        todoRepository.delete(todo);
        return todo.getId();
    }

    /**
     * PK인 ID를 기준으로 DB를 조회했을 때 단일 객체 반환 메서드
     * @param id RequestDto 의 id
     * @return 단일 객체 Get
     */
    private Todo findTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("선택한 일정이 존재하지 않습니다."));
        return todo;
    }
}
