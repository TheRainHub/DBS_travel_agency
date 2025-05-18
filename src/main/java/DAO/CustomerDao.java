package DAO;

import entities.Customer;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class CustomerDao extends BaseDao<Customer> {

    public CustomerDao(EntityManager em) {
        super(em, Customer.class);
    }

    /**
     * select everybody who was registered after a certain date
     * */
    public List<Customer> findRegisteredAfter(LocalDate date) {
        return entityManager.createQuery(
                        "SELECT c FROM Customer c WHERE c.registrationDate > :d", Customer.class)
                .setParameter("d", date)
                .getResultList();
    }
}
