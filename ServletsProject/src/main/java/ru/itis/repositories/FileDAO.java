package ru.itis.repositories;

import ru.itis.models.FileInfo;

public interface FileDAO extends CrudDAO<FileInfo>{
    FileInfo getById(int id);
}
