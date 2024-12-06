package ru.itis.models;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    private String content;
    private Timestamp createdAt;
    private int userId;
    private int taskId;
}

