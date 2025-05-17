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

    /**
     * Записать лог-операцию
     * @param op  – тип операции, например "INSERT User" или "DELETE Booking"
     * @param recordId – id того, к чему относится операция
     */
    public void log(String op, Long recordId) {
        // Build the log entry
        Log entry = new Log();
        entry.setOperation(op);
        entry.setOperationTime(LocalDateTime.now());
        entry.setRecordId(recordId);

        // Delegate to BaseDao.runInTx (via LogDao.save)
        logDao.save(entry);
    }

}
