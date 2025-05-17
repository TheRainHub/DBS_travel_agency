package DAO;

import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.Optional;

public class UserDao extends BaseDao<User> {

    public UserDao(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    public Optional<User> findByUsername(String username) {
        try {
            User user = entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
