package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "\"Booking\"")
public class Booking {
    @Id
    @Column(name = "booking_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private entities.Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_id", nullable = false)
    private entities.Tour tour;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "payment_day")
    private LocalDate paymentDay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public entities.Customer getCustomer() {
        return customer;
    }

    public void setCustomer(entities.Customer customer) {
        this.customer = customer;
    }

    public entities.Tour getTour() {
        return tour;
    }

    public void setTour(entities.Tour tour) {
        this.tour = tour;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(LocalDate paymentDay) {
        this.paymentDay = paymentDay;
    }

}