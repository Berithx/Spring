package com.homework.todo.entity;

import com.homework.todo.dto.TodoRequestDto;
import com.homework.todo.dto.TodoResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "todo")
@NoArgsConstructor
public class Todo extends Date{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;
    @Column(name = "manager", nullable = false)
    private String manager;
    @Column(name = "password", nullable = false)
    private String password;

    public Todo(TodoRequestDto todoRequestDto) {
        this.title = todoRequestDto.getTitle();
        this.contents = todoRequestDto.getContents();
        this.manager = todoRequestDto.getManager();
        this.password = todoRequestDto.getPassword();
    }
}
