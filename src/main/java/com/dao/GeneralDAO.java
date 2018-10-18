package com.dao;

import com.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

public abstract class GeneralDAO<T> {

    private Class typeOfClass;
    private HibernateUtil hibernateUtil;

    public void setTypeOfClass(Class typeOfClass) {
        this.typeOfClass = typeOfClass;
    }

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public void update(T obj) {
        try (Session session = hibernateUtil.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            session.update(obj);

            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Updating is failed");
        }
    }

    public void save(T obj) {
        try (Session session = hibernateUtil.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            session.save(obj);

            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Saving is failed");
        }
    }

    public void delete(long id, String deleteRequest) {
        try (Session session = hibernateUtil.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            NativeQuery query = session.createNativeQuery(deleteRequest);
            query.addEntity(typeOfClass);
            query.setParameter("id", id);

            query.executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Deleting file with id " + id + " is failed");
        } catch (IllegalArgumentException e) {
            System.out.println("File with id " + id + " was not found");
        }
    }

    @SuppressWarnings("unchecked")
    public T findById(long id) {
        try (Session session = hibernateUtil.openSession()) {
            return (T) session.get(typeOfClass, id);
        } catch (HibernateException e) {
            System.out.println("File with id " + id + " was not found");
        }

        return null;
    }
}
