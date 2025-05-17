package service;

import DAO.BookingDao;
import DAO.TourDao;
import entities.Tour;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.List;

public class TourService {

    private final EntityManager em;
    private final TourDao tourDao;
    private final BookingDao bookingDao;
    private final LoggingService logService;

    public TourService(EntityManager em) {
        this.em = em;
        this.tourDao = new TourDao(em);
        this.bookingDao = new BookingDao(em);
        this.logService = new LoggingService(em);
    }

    /**
     * 1) Создать новый тур
     */
    public Tour createTour(String name, LocalDate startDate, String description, long capacity, long price, String city, String street, long buildingNumber) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Tour t = new Tour();
            t.setTourName(name);
            t.setStartDate(startDate);
            t.setDescription(description);
            t.setCapacity(capacity);
            t.setPrice(price);
            t.setCity(city);
            t.setStreet(street);
            t.setBuildingNumber(buildingNumber);
            tourDao.save(t);
            logService.log("INSERT Tour", t.getId().longValue());
            tx.commit();
            return t;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }

    /**
     * 2) Обновить выбранные поля тура
     */
    public Tour updateTour(int tourId, String newDescription, Long newCapacity, Long newPrice) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Tour t = tourDao.findById(tourId);
            if (t == null) throw new IllegalArgumentException("Tour not found: " + tourId);
            if (newDescription != null) t.setDescription(newDescription);
            if (newCapacity != null) t.setCapacity(newCapacity);
            if (newPrice != null) t.setPrice(newPrice);
            tourDao.update(t);
            logService.log("UPDATE Tour", (long) tourId);
            tx.commit();
            return t;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }

    /**
     * 3) Найти все туры в указанном городе
     */
    public List<Tour> findByCity(String city) {
        return em.createQuery("SELECT t FROM Tour t WHERE t.city = :city", Tour.class).setParameter("city", city).getResultList();
    }

    /**
     * 4) Найти туры в заданном диапазоне дат
     */
    public List<Tour> findByDateRange(LocalDate from, LocalDate to) {
        return em.createQuery("SELECT t FROM Tour t " + "WHERE t.startDate BETWEEN :from AND :to", Tour.class).setParameter("from", from).setParameter("to", to).getResultList();
    }

    /**
     * 5) Показать туры, где свободных мест больше нуля
     */
    public List<Tour> findAvailableTours() {
        return em.createQuery("SELECT t FROM Tour t " + "WHERE t.capacity > (" + "SELECT COUNT(b) FROM Booking b WHERE b.tour.id = t.id" + ")", Tour.class).getResultList();
    }

    /**
     * 6) Зарезервировать место (уменьшаем capacity на 1)
     */
    public boolean reserveSpot(int tourId) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Tour t = tourDao.findById(tourId);
            if (t == null || t.getCapacity() <= 0) {
                tx.rollback();
                return false;
            }
            t.setCapacity(t.getCapacity() - 1);
            tourDao.update(t);
            logService.log("UPDATE Tour capacity reserve", (long) tourId);
            tx.commit();
            return true;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }

    /**
     * 7) Отменить бронь места (увеличиваем capacity на 1)
     */
    public boolean cancelReservation(int tourId) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Tour t = tourDao.findById(tourId);
            if (t == null) {
                tx.rollback();
                return false;
            }
            t.setCapacity(t.getCapacity() + 1);
            tourDao.update(t);
            logService.log("UPDATE Tour capacity cancel", (long) tourId);
            tx.commit();
            return true;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }

    /**
     * 8) Отменить тур целиком (удалить тур и все связанные бронирования)
     */
    public void cancelTour(int tourId) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // 8.1 удалить все Booking для этого тура
            em.createQuery("DELETE FROM Booking b WHERE b.tour.id = :tid").setParameter("tid", tourId).executeUpdate();
            logService.log("DELETE Booking by Tour", (long) tourId);
            // 8.2 удалить сам тур
            tourDao.delete(tourDao.findById(tourId));
            logService.log("DELETE Tour", (long) tourId);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        }
    }
}
