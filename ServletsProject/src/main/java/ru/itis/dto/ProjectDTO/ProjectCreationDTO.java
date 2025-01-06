package ru.itis.dto.ProjectDTO;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectCreationDTO {
    private String name;
    private String description;
    private int createdBy;
}
