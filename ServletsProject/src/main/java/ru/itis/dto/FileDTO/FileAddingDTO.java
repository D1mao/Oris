package ru.itis.dto.FileDTO;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileAddingDTO {
    private String originalFileName;
    private int size;
    private String type;
}
