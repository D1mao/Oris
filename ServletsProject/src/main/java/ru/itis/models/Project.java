package ru.itis.models;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private String name;
    private String description;
    private int createdBy;
    private int id;
    private Timestamp createdAt;
}

