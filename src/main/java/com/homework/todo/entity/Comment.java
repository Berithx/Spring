package com.homework.todo.entity;

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

    @Column(name = "contents", nullable = false, length = 500)
    private String comment;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;
}
