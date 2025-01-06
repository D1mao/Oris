package ru.itis.services;

import ru.itis.dto.FileDTO.FileAddingDTO;
import ru.itis.dto.FileDTO.FileDTO;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileService {
    public void saveFileToStorage(InputStream file, FileAddingDTO fileDTO);
    public void writeFileFromStorage(Integer id, OutputStream outputStream);
    public FileDTO getFileInfo(Integer id);
}
