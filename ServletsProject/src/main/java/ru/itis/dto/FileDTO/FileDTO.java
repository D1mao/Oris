package ru.itis.dto.FileDTO;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileDTO {
    private int id;
    private String originalFileName;
    private String storageFileName;
    private String type;
}
