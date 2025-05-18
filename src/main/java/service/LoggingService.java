package service;

import DAO.LogDao;
import entities.Log;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;

public class LoggingService {

    private final LogDao logDao;
    private final EntityManager em;

    public LoggingService(EntityManager em) {
        this.em    = em;
        this.logDao = new LogDao(em);
    }

    public void log(String op, Long recordId) {
        Log entry = new Log();
        entry.setOperation(op);
        entry.setOperationTime(LocalDateTime.now());
        entry.setRecordId(recordId);

        logDao.save(entry);
    }

}
