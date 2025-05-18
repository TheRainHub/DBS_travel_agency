package service;

import DAO.BookingDao;
import DAO.CustomerDao;
import DAO.ReviewDao;
import DAO.TourDao;
import entities.Booking;
import entities.Customer;
import entities.Review;
import entities.Tour;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;

public class CustomerService {

    private final EntityManager em;

    private final CustomerDao customerDao;
    private final BookingDao bookingDao;
    private final ReviewDao reviewDao;
    private final TourDao tourDao;
    private final LoggingService logService;

    public CustomerService(EntityManager em) {
        this.em = em;

        customerDao = new CustomerDao(em);
        bookingDao = new BookingDao(em);
        reviewDao = new ReviewDao(em);
        tourDao = new TourDao(em);
        this.logService = new LoggingService(em);
    }

    public Customer register(String username, String email) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Customer c = new Customer();
            c.setUsername(username);
            c.setEmail(email);
            c.setRegistrationDate(LocalDate.now());

            customerDao.save(c);                  // INSERT User + Customer
            logService.log("INSERT Customer", c.getId().longValue());
            tx.commit();
            return c;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }

    public Booking makeBooking(int customerId, int tourId) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Customer c = customerDao.findById(customerId);
            Tour t = tourDao.findById(tourId);
            if (c == null || t == null) throw new IllegalArgumentException("Customer/Tour not found");

            Booking b = new Booking();
            b.setCustomer(c);
            b.setTour(t);
            b.setBookingDate(LocalDate.now());
            b.setStatus("NEW");

            bookingDao.save(b);
            logService.log("INSERT Booking", b.getId().longValue());
            tx.commit();
            return b;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }

    public void confirmPayment(int bookingId) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Booking b = bookingDao.findById(bookingId);
            if (b == null) throw new IllegalArgumentException("Booking not found");

            b.setStatus("PAID");
            b.setPaymentDay(LocalDate.now());
            bookingDao.update(b);
            logService.log("UPDATE Booking", (long) bookingId);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }

    public Review leaveReview(int customerId, int tourId, int rating, String comment) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Customer c = customerDao.findById(customerId);
            Tour t = tourDao.findById(tourId);
            if (c == null || t == null) throw new IllegalArgumentException("Customer/Tour not found");

            Review r = new Review();
            r.setCustomer(c);
            r.setTour(t);
            r.setReviewDate(LocalDate.now());
            r.setRating((long) rating);
            r.setComment(comment);

            reviewDao.save(r);
            logService.log("INSERT Review", r.getId().longValue());
            tx.commit();
            return r;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }
    public void deleteCustomer(int customerId) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.createQuery("DELETE FROM Review r WHERE r.customer.id = :cid").setParameter("cid", customerId).executeUpdate();

            em.createQuery("DELETE FROM Booking b WHERE b.customer.id = :cid").setParameter("cid", customerId).executeUpdate();

            em.createQuery("DELETE FROM Customer c WHERE c.id = :cid").setParameter("cid", customerId).executeUpdate();

            logService.log("DELETE Customer", (long) customerId);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }

    public List<Booking> findActiveBookings(int customerId) {
        return em.createQuery("""
                SELECT b FROM Booking b
                WHERE b.customer.id = :cid
                  AND b.status IN ('NEW','PAID')
                """, Booking.class).setParameter("cid", customerId).getResultList();
    }
}
