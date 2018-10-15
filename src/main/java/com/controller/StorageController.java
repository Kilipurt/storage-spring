package com.controller;

import com.entity.File;
import com.entity.Storage;
import com.service.FileService;
import com.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StorageController {

    private FileService fileService;
    private StorageService storageService;

    @Autowired
    public StorageController(FileService fileService, StorageService storageService) {
        this.fileService = fileService;
        this.storageService = storageService;
    }

    public void put(Storage storage, File file) throws Exception {
        fileService.put(storage, file);
    }

    public void delete(Storage storage, File file) throws Exception {
        fileService.delete(storage, file);
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        fileService.transferAll(storageFrom, storageTo);
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        fileService.transferFile(storageFrom, storageTo, id);
    }

    public void saveFile(File file) {
        fileService.save(file);
    }

    public void deleteFileFromDb(long id) {
        fileService.deleteFromDb(id);
    }

    public void updateFile(File file) throws Exception {
        fileService.update(file);
    }

    public File findFileById(long id) throws Exception {
        return fileService.findById(id);
    }

    public void saveStorage(Storage storage) {
        storageService.save(storage);
    }

    public void deleteStorage(long id) {
        storageService.delete(id);
    }

    public void updateStorage(Storage storage) throws Exception {
        storageService.update(storage);
    }

    public Storage findStorageById(long id) throws Exception {
        return storageService.findById(id);
    }
}
