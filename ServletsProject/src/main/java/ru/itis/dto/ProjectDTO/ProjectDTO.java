package ru.itis.dto.ProjectDTO;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectDTO {
    private String name;
    private String description;
    private String createdBy;
    private int id;
    private Timestamp createdAt;
}
