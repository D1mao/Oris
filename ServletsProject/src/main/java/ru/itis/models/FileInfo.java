package ru.itis.models;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    private String originalFileName;
    private int size;
    private String type;
    private int id;
    private String storageFileName;
}
