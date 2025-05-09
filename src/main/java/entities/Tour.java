package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"Tour\"")
public class Tour {
    @Id
    @Column(name = "tour_id", nullable = false)
    private Integer id;

    @Column(name = "tour_name", nullable = false, length = 100)
    private String tourName;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "description")
    private String description;

    @Column(name = "capacity", nullable = false)
    private Long capacity;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "street", length = 100)
    private String street;

    @Column(name = "building_number")
    private Long buildingNumber;

    @OneToMany(mappedBy = "tour")
    private Set<Booking> bookings = new LinkedHashSet<>();

    @ManyToMany
    private Set<entities.Worker> workers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "tour")
    private Set<Review> reviews = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Long buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<entities.Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<entities.Worker> workers) {
        this.workers = workers;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

}