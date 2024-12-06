package ru.itis.models;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private int id;
    private String name;
    private String description;
    private Timestamp createdAt;
    private int createdBy;
}

