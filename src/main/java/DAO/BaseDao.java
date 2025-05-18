//package DAO;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityTransaction;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Supplier;
//
//public abstract class BaseDao<T> {
//
//    protected final EntityManager entityManager;
//    private final Class<T> entityClass;
//
//    public BaseDao(EntityManager em, Class<T> entityClass) {
//        this.entityManager = em;
//        this.entityClass   = entityClass;
//    }
//
//    protected <R> R runInTx(Supplier<R> action) {
//        EntityTransaction tx = entityManager.getTransaction();
//        boolean newTx = !tx.isActive();
//        try {
//            if (newTx) tx.begin();
//            R res = action.get();
//            if (newTx) tx.commit();
//            return res;
//        } catch (RuntimeException e) {
//            if (newTx && tx.isActive()) tx.rollback();
//            throw e;
//        }
//    }
//
//    public void save(T entity) {
//        runInTx(() -> { entityManager.persist(entity); return null; });
//    }
//
//    public T update(T entity) {
//        return runInTx(() -> entityManager.merge(entity));
//    }
//
//    public void delete(T entity) {
//        runInTx(() -> {
//            entityManager.remove(
//                    entityManager.contains(entity)
//                            ? entity
//                            : entityManager.merge(entity));
//            return null;
//        });
//    }
//
//    public T findById(Object id) {
//        return entityManager.find(entityClass, id);
//    }
//
//    public List<T> findAll() {
//        return entityManager.createQuery(
//                        "SELECT e FROM " + entityClass.getSimpleName() + " e",
//                        entityClass)
//                .getResultList();
//    }
//}
