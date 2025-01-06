package ru.itis.dto.CommentDTO;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentCreationDTO {
    private String content;
    private int userId;
    private int taskId;
}
