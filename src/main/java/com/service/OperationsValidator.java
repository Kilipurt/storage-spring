package com.service;

import com.dao.FileDAO;
import com.dao.StorageDAO;
import com.entity.File;
import com.entity.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationsValidator {
    private StorageDAO storageDAO;
    private FileDAO fileDAO;

    @Autowired
    public OperationsValidator(StorageDAO storageDAO, FileDAO fileDAO) {
        this.storageDAO = storageDAO;
        this.fileDAO = fileDAO;
    }

    public void validateStorageUpdate(Storage storage) throws Exception {
        checkStorageForExistence(storage);

        Storage storageForUpdate = storageDAO.findById(storage.getId());
        if (storage.getStorageSize() < storageForUpdate.getStorageSize() && filesContainedSize(storage) > storage.getStorageSize()) {
            throw new Exception("Can't update storage " + storage.getId() + ". Not enough space for files");
        }

        if (!storage.getFormatsSupportedString().equals(storageForUpdate.getFormatsSupportedString())) {
            List<File> files = fileDAO.findFilesByStorageId(storage.getId());

            for (File file : files) {
                checkFormat(storage, file);
            }
        }
    }

    public void validateFileUpdate(File file) throws Exception {
        File fileForUpdate = fileDAO.findById(file.getId());

        if (fileForUpdate == null) {
            throw new Exception("File with id " + file.getId() + " was not found");
        }

        if (fileForUpdate.getStorage() == null) {
            return;
        }

        checkParametersOfUpdatedFile(file, fileForUpdate);
    }

    public void validateFilesTransfer(Storage storageFrom, Storage storageTo) throws Exception {
        checkStorageForExistence(storageFrom);
        checkStorageForExistence(storageTo);

        if (filesContainedSize(storageFrom) > storageTo.getStorageSize() - filesContainedSize(storageTo)) {
            throw new Exception("Not enough space for transfer all files from storage "
                    + storageFrom.getId() + " to storage " + storageTo.getId());
        }

        List<File> files = fileDAO.findFilesByStorageId(storageFrom.getId());
        for (File file : files) {
            checkFormat(storageTo, file);
        }
    }

    public void validateFileTransfer(Storage storageFrom, Storage storageTo, File file) throws Exception {
        checkStorageForExistence(storageFrom);
        checkStorageForExistence(storageTo);
        checkFileForExistence(file);
        checkFileInStorage(storageFrom, file);
        checkFormat(storageTo, file);
        checkFreeSpace(storageTo, file);
    }

    public void validateFileDelete(Storage storage, File file) throws Exception {
        checkStorageForExistence(storage);
        checkFileForExistence(file);

        File deletedFile = fileDAO.findById(file.getId());
        checkFileInStorage(storage, deletedFile);
    }

    public void validateFilePut(Storage storage, File file) throws Exception {
        checkStorageForExistence(storage);
        checkFileForExistence(file);
        checkFormat(storage, file);
        checkFreeSpace(storage, file);
    }

    private void checkFreeSpace(Storage storage, File file) throws Exception {
        if (!isEnoughSpaceForFile(storage, file)) {
            throw new Exception("Not enough space for put file " + file.getId()
                    + " to storage " + storage.getId());
        }
    }

    private void checkFormat(Storage storage, File file) throws Exception {
        if (!storage.isFormatSupported(file.getFormat())) {
            throw new Exception("File " + file.getId() + " was not put to storage "
                    + storage.getId() + ". Format isn't supported.");
        }
    }

    private void checkStorageForExistence(Storage storage) throws NullPointerException {
        if (storage == null || !storage.equals(storageDAO.findById(storage.getId())))
            throw new NullPointerException("Storage was not found");
    }

    private void checkFileForExistence(File file) throws NullPointerException {
        if (file == null || !file.equals(fileDAO.findById(file.getId())))
            throw new NullPointerException("File was not found");
    }

    private boolean isEnoughSpaceForFile(Storage storage, File file) {
        return filesContainedSize(storage) + file.getSize() <= storage.getStorageSize();
    }

    private long filesContainedSize(Storage storage) {
        long filesSize = 0;

        List<File> filesInStorage = fileDAO.findFilesByStorageId(storage.getId());
        for (File file : filesInStorage) {
            filesSize += file.getSize();
        }

        return filesSize;
    }

    private void checkFileInStorage(Storage storage, File file) throws Exception {
        if (file.getStorage() == null || file.getStorage().getId() != storage.getId())
            throw new Exception("Storage " + storage.getId() + " does not contain file " + file.getId());
    }

    private void checkParametersOfUpdatedFile(File file, File fileForUpdate) throws Exception {
        Storage storage = fileForUpdate.getStorage();

        if (!storage.equals(file.getStorage())) {
            throw new Exception("Can't update file " + file.getId() + " storage");
        }

        if (file.getSize() > fileForUpdate.getSize()) {
            long newFilesSize = filesContainedSize(storage) - file.getSize() + fileForUpdate.getSize();
            if (newFilesSize > storage.getStorageSize()) {
                throw new Exception("Can't update file " + file.getId() + ". Not enough space in storage " + storage.getId());
            }
        }

        if (!file.getFormat().equals(fileForUpdate.getFormat()) && !storage.isFormatSupported(file.getFormat())) {
            throw new Exception("Can't update file " + file.getId() + ". Format isn't supported");
        }
    }
}
