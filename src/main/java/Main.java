import entities.WorkerLanguage;
import jakarta.persistence.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpaUnit");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            List<WorkerLanguage> results = em.createQuery("SELECT wl FROM WorkerLanguage wl", WorkerLanguage.class)
                    .setMaxResults(10)
                    .getResultList();

            for (WorkerLanguage wl : results) {
                System.out.println("Worker ID: " + wl.getWorker().getId()
                        + ", Language ID: " + wl.getLanguage().getId());
            }

            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }
    }
}
