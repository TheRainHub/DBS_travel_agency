package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"Customer\"")
@PrimaryKeyJoinColumn(
        name = "customer_id",
        referencedColumnName = "user_id"
)
public class Customer extends User {
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "customer")
    private Set<Booking> bookings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<entities.Review> reviews = new LinkedHashSet<>();

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