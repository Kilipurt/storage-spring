package com;

import com.controller.Controller;
import com.dao.FileDAO;
import com.dao.StorageDAO;
import com.entity.File;
import com.entity.Storage;
import com.service.FileService;
import com.service.OperationsValidator;
import com.service.StorageService;
import com.util.HibernateUtil;

public class Demo {
    private static StorageDAO storageDAO = new StorageDAO(new HibernateUtil());
    private static FileDAO fileDAO = new FileDAO(new HibernateUtil());
    private static OperationsValidator operationsValidator = new OperationsValidator(storageDAO, fileDAO);
    private static FileService fileService = new FileService(fileDAO, operationsValidator);
    private static StorageService storageService = new StorageService(storageDAO, operationsValidator);

    private static Controller controller = new Controller(fileService, storageService);

    public static void main(String[] args) {
        File file1 = createFile("first", "txt", 25, null);
        File file2 = createFile("second", "mp4", 30, null);
        File file3 = createFile("first", "txt", 25, null);
//        controller.saveFile(file2);
//        controller.saveFile(file1);
        Storage storage1 = createStorage(new String[]{"txt", "jpeg", "mp4"}, "Germany", 50);
        Storage storage2 = createStorage(new String[]{"txt", "jpeg", "xml"}, "Germany", 50);
//        controller.saveStorage(storage2);
//        controller.saveStorage(storage1);

        file1.setId(1);
        file2.setId(3);
        file3.setId(2);
        storage1.setId(6);
        storage2.setId(7);

        file2.setSize(2);
//        controller.updateFile(file2);


        try {
//            controller.put(storage1, file1);
//            controller.put(storage1, file2);
//            controller.put(storage2, file3);

//            controller.transferAll(storage2, storage1);

            controller.transferFile(storage1, storage2, 3);

//            controller.delete(storage2, file2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static File createFile(String name, String format, long size, Storage storage) {
        File file = new File();
        file.setStorage(storage);
        file.setFormat(format);
        file.setName(name);
        file.setSize(size);
        return file;
    }

    private static Storage createStorage(String[] formatsSupported, String country, long size) {
        Storage storage = new Storage();
        storage.setFormatsSupported(formatsSupported);
        storage.setStorageSize(size);
        storage.setStorageCountry(country);
        return storage;
    }
}
