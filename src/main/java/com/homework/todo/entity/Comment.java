package com.homework.todo.entity;

import com.homework.todo.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends Date {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment", nullable = false, length = 500)
    private String comment;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @ManyToOne
    @JoinColumn(name = "todo_Id", nullable = false)
    private Todo todo;

    public Comment(Todo todo, CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
        this.username = requestDto.getUsername();
        this.todo = todo;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
