package service;

import DAO.*;
import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class WorkerService {

    private final EntityManager em;
    private final WorkerDao workerDao;
    private final PositionDao positionDao;
    private final LanguageDao languageDao;
    private final LoggingService logService;

    public WorkerService(EntityManager em) {
        this.em = em;
        workerDao = new WorkerDao(em);
        positionDao = new PositionDao(em);
        languageDao = new LanguageDao(em);
        this.logService = new LoggingService(em);
    }

    /* === 1. CP-4 транзакция: нанять работника, создавая позицию при необходимости === */
    public Worker hire(String username, String email,
                       String positionName, long salary, long exp) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Position pos = positionDao.findByName(positionName)
                    .orElseGet(() -> {
                        Position p = new Position();
                        p.setName(positionName);
                        positionDao.save(p);
                        return p;
                    });

            Worker w = new Worker();
            w.setUsername(username);
            w.setEmail(email);
            w.setPosition(pos);
            w.setSalary(salary);
            w.setExperience(exp);

            workerDao.save(w);
            logService.log("INSERT Worker", w.getId().longValue());
            tx.commit();
            return w;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    /* === 2. Повыcить зарплату всем с опытом ≥ minExp === */
    public int raiseSalary(long minExp, long delta) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        int updated = em.createQuery(
                        "UPDATE Worker w SET w.salary = w.salary + :d " +
                                "WHERE w.experience >= :e")
                .setParameter("d", delta)
                .setParameter("e", minExp)
                .executeUpdate();
        tx.commit();
        logService.log("UPDATE Workers salary", (long) updated);
        return updated;
    }

    /* === 3. Уволить работника === */
    public void fire(int workerId) {
        Worker w = workerDao.findById(workerId);
        if (w == null) return;
        workerDao.delete(w);
        logService.log("DELETE Worker", (long) workerId);
    }

    /* === 4. Добавить знание языка (Many-to-Many) === */
    public void addLanguage(int workerId, String langId) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 1) найдём работника
            Worker w = workerDao.findById(workerId);
            if (w == null) {
                throw new IllegalArgumentException("Worker not found: " + workerId);
            }

            // 2) найдём или создадим язык
            Language l = languageDao.findById(langId);
            if (l == null) {
                l = new Language();
                l.setLanguageId(langId);
                languageDao.save(l);    // внутри save() благодаря BaseDao.runInTx() не откроет новую транзакцию
                logService.log("INSERT language", (long) workerId);
            }

            // 3) свяжем друг с другом
            w.getLanguages().add(l);
            l.getWorkers().add(w);
            logService.log("INSERT Worker_language", (long) workerId);

            // 4) обновим состояние в БД
            workerDao.update(w);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


    /* === 5. Вернуть всех гидов-миллионеров === */
    public List<Worker> richGuides(long threshold) {
        return em.createQuery(
                        "SELECT w FROM Worker w " +
                                "WHERE w.position.name = 'Guide' AND w.salary >= :t",
                        Worker.class)
                .setParameter("t", threshold)
                .getResultList();
    }
}
