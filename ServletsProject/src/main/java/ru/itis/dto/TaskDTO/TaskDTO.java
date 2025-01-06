package ru.itis.dto.TaskDTO;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private String status;
    private Timestamp createdAt;
    private Date dueDate;
    private int projectId;
    private String assignedUser;
    private Integer fileId;
}
