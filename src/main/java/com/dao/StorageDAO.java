package com.dao;

import com.entity.Storage;
import com.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class StorageDAO extends GeneralDAO<Storage> {

    private static final String DELETE_REQUEST = "DELETE FROM STORAGE WHERE ID = :id";

    private HibernateUtil hibernateUtil;

    @Autowired
    public StorageDAO(HibernateUtil hibernateUtil) {
        setTypeOfClass(Storage.class);
        setDeleteRequest(DELETE_REQUEST);
        setHibernateUtil(hibernateUtil);
    }

    @Override
    public void update(Storage obj) {
        super.update(obj);
    }

    @Override
    public void save(Storage obj) {
        obj.setFormatsSupportedString(obj.formatsSupportedToString());
        super.save(obj);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public Storage findById(long id) {
        Storage storage = super.findById(id);
        storage.setFormatsSupported(storage.formatsSupportedToArray());

        return storage;
    }
}