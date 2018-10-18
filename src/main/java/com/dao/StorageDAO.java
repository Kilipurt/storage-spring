package com.dao;

import com.entity.Storage;
import com.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StorageDAO extends GeneralDAO<Storage> {

    private static final String DELETE_REQUEST = "DELETE FROM STORAGE WHERE ID = :id";

    @Autowired
    public StorageDAO(HibernateUtil hibernateUtil) {
        setTypeOfClass(Storage.class);
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
    public void delete(long id, String deleteRequest) {
        super.delete(id, DELETE_REQUEST);
    }

    @Override
    public Storage findById(long id) {
        Storage storage = super.findById(id);
        storage.setFormatsSupported(storage.formatsSupportedToArray());

        return storage;
    }
}
