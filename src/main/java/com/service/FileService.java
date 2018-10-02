package com.service;

import com.dao.FileDAO;
import com.entity.File;
import com.entity.Storage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FileService {

    private FileDAO fileDAO;
    private OperationsValidator operationsValidator;

    @Autowired
    public FileService(FileDAO fileDAO, OperationsValidator operationsValidator) {
        this.fileDAO = fileDAO;
        this.operationsValidator = operationsValidator;
    }

    public void save(File file) {
        fileDAO.save(file);
    }

    public void update(File file) throws Exception {
        operationsValidator.validateFileUpdate(file);
        fileDAO.update(file);
    }

    public void deleteFromDb(long id) {
        fileDAO.delete(id);
    }

    public File findById(long id) throws Exception {
        File file = fileDAO.findById(id);

        if (file == null) {
            throw new Exception("File with id " + id + " was not found");
        }

        return file;
    }

    public void put(Storage storage, File file) throws Exception {
        operationsValidator.validateFilePut(storage, file);

        file.setStorage(storage);
        fileDAO.update(file);
    }

    public void delete(Storage storage, File file) throws Exception {
        operationsValidator.validateFileDelete(storage, file);

        file.setStorage(null);
        fileDAO.update(file);
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        operationsValidator.validateFilesTransfer(storageFrom, storageTo);
        List<File> files = fileDAO.findFilesByStorageId(storageFrom.getId());

        for (File file : files) {
            file.setStorage(storageTo);
            fileDAO.update(file);
        }
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        File file = fileDAO.findById(id);
        operationsValidator.validateFileTransfer(storageFrom, storageTo, file);
        file.setStorage(storageTo);
        fileDAO.update(file);
    }
}
