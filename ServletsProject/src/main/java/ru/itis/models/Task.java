package ru.itis.models;

import lombok.*;

import java.sql.Timestamp;
import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private int id;
    private String title;
    private String description;
    private String status; // 'IN_PROGRESS' или 'COMPLETED'
    private Date dueDate;
    private Timestamp createdAt;
    private int projectId;
    private int userId;
}

