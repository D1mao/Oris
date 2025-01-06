package ru.itis.dto.TaskDTO;

import lombok.*;

import java.sql.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskCreationDTO {
    private String title;
    private String description;
    private Date dueDate;
    private int projectId;
    private Integer fileId;
}
