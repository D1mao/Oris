package ru.itis.services.impl;

import ru.itis.dto.FileDTO.FileAddingDTO;
import ru.itis.dto.FileDTO.FileDTO;
import ru.itis.models.FileInfo;
import ru.itis.repositories.FileDAO;
import ru.itis.services.FileService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService {
    private FileDAO fileDAO;

    public FileServiceImpl(FileDAO fileDAO) {this.fileDAO = fileDAO;}

    @Override
    public void saveFileToStorage(InputStream file, FileAddingDTO fileDTO) {
        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(fileDTO.getOriginalFileName())
                .storageFileName(UUID.randomUUID().toString())
                .size(fileDTO.getSize())
                .type(fileDTO.getType())
                .build();

        try {
            Files.copy(file, Paths.get("C://jaba/ServletProjectFiles/" + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
            fileDAO.save(fileInfo);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void writeFileFromStorage(Integer id, OutputStream outputStream) {
        FileInfo fileInfo = fileDAO.getById(id);
        File file = new File("C://jaba/ServletProjectFiles/" + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
        try {
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public FileDTO getFileInfo(Integer id) {
        FileInfo fileInfo = fileDAO.getById(id);

        FileDTO fileResponseDTO = FileDTO.builder()
                .id(fileInfo.getId())
                .storageFileName(fileInfo.getStorageFileName())
                .originalFileName(fileInfo.getOriginalFileName())
                .type(fileInfo.getType())
                .build();

        return fileResponseDTO;
    }
}
