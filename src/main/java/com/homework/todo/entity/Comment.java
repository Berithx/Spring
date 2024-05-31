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

    @ManyToOne
    @JoinColumn(name = "todo_Id", nullable = false)
    private Todo todo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    public Comment(Todo todo, CommentRequestDto requestDto, User user) {
        this.comment = requestDto.getComment();
        this.todo = todo;
        this.user = user;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
