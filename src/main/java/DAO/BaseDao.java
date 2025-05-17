package DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.Connection;
import java.util.List;

public abstract class BaseDao<T> {

    protected final EntityManager entityManager;  // Должно быть protected
    private final Class<T> entityClass;
    protected final Connection connection;


    public BaseDao(EntityManager entityManager, Class<T> entityClass,Connection connection) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.connection = connection;
    }

    public void save(T entity) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public T findById(Object id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        return entityManager
                .createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                .getResultList();
    }

    public T update(T entity) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            T merged = entityManager.merge(entity);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(T entity) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
