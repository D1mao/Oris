package ru.itis.dto.CommentDTO;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDTO {
    private String content;
    private String userName;
    private int taskId;
    private int id;
    private Timestamp createdAt;
}
