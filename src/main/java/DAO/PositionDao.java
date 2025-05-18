//package DAO;
//
//import entities.Position;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.NoResultException;
//
//import java.util.Optional;
//
//public class PositionDao extends BaseDao<Position> {
//    public PositionDao(EntityManager em) {
//        super(em, Position.class);
//    }
//
//    public Optional<Position> findByName(String name) {
//        try {
//            Position p = entityManager.createQuery(
//                            "SELECT p FROM Position p WHERE p.name = :name", Position.class)
//                    .setParameter("name", name)
//                    .getSingleResult();
//            return Optional.of(p);
//        } catch (NoResultException e) {
//            return Optional.empty();
//        }
//    }
//}
