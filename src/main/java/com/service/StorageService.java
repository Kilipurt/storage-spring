package com.service;

import com.dao.StorageDAO;
import com.entity.Storage;
import org.springframework.beans.factory.annotation.Autowired;

public class StorageService {

    private StorageDAO storageDAO;
    private OperationsValidator operationsValidator;

    @Autowired
    public StorageService(StorageDAO storageDAO, OperationsValidator operationsValidator) {
        this.storageDAO = storageDAO;
        this.operationsValidator = operationsValidator;
    }

    public void save(Storage storage) {
        storageDAO.save(storage);
    }

    public void update(Storage storage) throws Exception {
        storage.setFormatsSupportedString(storage.formatsSupportedToString());
        operationsValidator.validateStorageUpdate(storage);
        storageDAO.update(storage);
    }

    public void delete(long id) {
        storageDAO.delete(id);
    }

    public Storage findById(long id) throws Exception {
        Storage storage = storageDAO.findById(id);

        if (storage == null) {
            throw new Exception("Storage with id " + id + " was not found");
        }

        return storage;
    }
}
