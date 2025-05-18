package DAO;

import entities.Booking;
import jakarta.persistence.EntityManager;

public class BookingDao extends BaseDao<Booking> {
    public BookingDao(EntityManager em) { super(em, Booking.class); }
}