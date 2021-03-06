package com.dao;

import com.entity.File;
import com.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FileDAO extends GeneralDAO<File> {

    private HibernateUtil hibernateUtil;

    private static final String DELETE_REQUEST = "DELETE FROM FILES WHERE ID = :id";
    private static final String FIND_BY_STORAGE_ID_REQUEST = "SELECT * FROM FILES WHERE FILES.STORAGE = :id";

    @Autowired
    public FileDAO(HibernateUtil hibernateUtil) {
        setTypeOfClass(File.class);
        this.hibernateUtil = hibernateUtil;
        setHibernateUtil(hibernateUtil);
    }

    @Override
    public void update(File obj) {
        super.update(obj);
    }

    @Override
    public void save(File obj) {
        super.save(obj);
    }

    public void delete(long id) {
        super.delete(id, DELETE_REQUEST);
    }

    @Override
    public File findById(long id) {
        return super.findById(id);
    }

    @SuppressWarnings("unchecked")
    public List<File> findFilesByStorageId(long id) {
        try (Session session = hibernateUtil.openSession()) {

            NativeQuery query = session.createNativeQuery(FIND_BY_STORAGE_ID_REQUEST);
            query.addEntity(File.class);
            query.setParameter("id", id);

            query.getResultList();

            List<File> files = query.list();
            for (File file : files) {
                file.getStorage().setFormatsSupported(file.getStorage().formatsSupportedToArray());
            }

            return files;
        } catch (HibernateException e) {
            System.err.println("Something went wrong");
        }

        return new ArrayList<>();
    }
}