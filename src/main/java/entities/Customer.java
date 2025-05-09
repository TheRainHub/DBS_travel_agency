package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"Customer\"")
public class Customer {
    @Id
    @Column(name = "customer_id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private entities.User user;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "customer")
    private Set<Booking> bookings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<entities.Review> reviews = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public entities.User getUser() {
        return user;
    }

    public void setUser(entities.User user) {
        this.user = user;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<entities.Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<entities.Review> reviews) {
        this.reviews = reviews;
    }

}