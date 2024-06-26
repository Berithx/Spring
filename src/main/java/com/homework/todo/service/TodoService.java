package com.homework.todo.service;

import com.homework.todo.dto.TodoRequestDto;
import com.homework.todo.dto.TodoResponseDto;
import com.homework.todo.entity.Todo;
import com.homework.todo.entity.User;
import com.homework.todo.jwt.JwtUtil;
import com.homework.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;


    @Transactional
    public TodoResponseDto createTodo(TodoRequestDto requestDto, String token) {
        User user = userService.findByUsername(jwtUtil.getUserInfoFromToken(token).getSubject());

        Todo todo = new Todo(requestDto, user);

        Todo saveTodo = todoRepository.save(todo);

        return new TodoResponseDto(todo);
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

    @Transactional
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto, String token) {
        if (!hasAccess(id, token)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }
        Todo todo = findTodoById(id);
        todo.checkPassword(userService.findUserByToken(token));
        todo.update(requestDto);
        return new TodoResponseDto(todo);
    }

    @Transactional
    public void deleteTodo(Long id, String token) {
        if (!hasAccess(id, token)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다");
        }
        Todo todo = findTodoById(id);
        todo.checkPassword(userService.findUserByToken(token));
        todoRepository.delete(todo);
    }

    private boolean hasAccess(Long id, String token) {
        String todoUsername = findTodoById(id).getUser().getUsername();
        return todoUsername.equals(jwtUtil.getUserInfoFromToken(token).getSubject());
    }

    /**
     * PK인 ID를 기준으로 DB를 조회했을 때 단일 객체 반환 메서드
     * @param id RequestDto 의 id
     * @return 단일 객체 Get
     */
    protected Todo findTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 일정입니다.")
        );
        return todo;
    }

     protected void existsById(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new NoSuchElementException("존재하지 않는 일정입니다.");
        }
    }
}
