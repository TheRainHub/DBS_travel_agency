package DAO;

import entities.Log;
import jakarta.persistence.EntityManager;

public class LogDao extends BaseDao<Log> {
    public LogDao(EntityManager em) {
        super(em, Log.class);
    }
}
