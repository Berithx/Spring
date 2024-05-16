package com.homework.todo.service;

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

    // DB 저장
    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        // RequestDto -> Entity 변환
        Todo todo = new Todo(requestDto);

        // DB 저장
        Todo saveTodo = todoRepository.save(todo);

        // Entity -> Response 변환
        TodoResponseDto todoResponseDto = new TodoResponseDto(todo);
        return todoResponseDto;
    }

    // DB 단일조회
    public TodoResponseDto getTodoById(Long id) {
        // DB 조회
        Todo todo = findForGetTodoById(id);
        return new TodoResponseDto(todo);
    }

    // DB 전체 일괄조회
    public List<TodoResponseDto> getTodo() {
        return todoRepository.findAllByOrderByDateDesc().stream().map(TodoResponseDto::new).toList();
    }

    // DB 특정 데이터 수정
    @Transactional
    public Long updateTodo(Long id, TodoRequestDto todoRequestDto) {
        Todo todo = findForUpdateTodoById(id);
        if (todo.getPassword().equals(todoRequestDto.getPassword())) {
            todo.update(todoRequestDto);
        } else {
            System.out.println("비밀번호가 일치하지 않습니다.");
        }

        return id;
    }

    /**
     * PK인 ID를 기준으로 DB를 조회했을 때 단일 객체 반환 메서드
     * @param id
     * @return 단일 객체 Get용 Todo 객체
     */
    private Todo findForGetTodoById(Long id) {
        return todoRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("선택한 일정이 존재하지 않습니다."));
    }

    /**
     * PK인 ID를 기준으로 DB를 조회했을 때 수정, 삭제용 객체 반환 메서드
     * @param id
     * @return update용 Todo 객체
     */
    private Todo findForUpdateTodoById(Long id) {
        return todoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정이 존재하지 않습니다.")
        );
    }
}
