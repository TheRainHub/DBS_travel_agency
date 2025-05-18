package DAO;

import entities.Worker;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class WorkerDao extends BaseDao<Worker> {

    public WorkerDao(EntityManager em) {
        super(em, Worker.class);
    }

    public Optional<Worker> findByUsername(String username) {
        return entityManager.createQuery(
                        "SELECT w FROM Worker w WHERE w.username = :u", Worker.class)
                .setParameter("u", username)
                .getResultStream()
                .findFirst();
    }


    public List<Worker> findByMinSalary(long minSalary) {
        return entityManager.createQuery(
                        "SELECT w FROM Worker w WHERE w.salary >= :s", Worker.class)
                .setParameter("s", minSalary)
                .getResultList();
    }
}
