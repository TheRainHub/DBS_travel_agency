package entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @OneToOne(mappedBy = "user")
    private Customer customer;

    @OneToMany(mappedBy = "user")
    private Set<PersonalDatum> personalData = new LinkedHashSet<>();

    @OneToOne(mappedBy = "user")
    private entities.Worker worker;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<PersonalDatum> getPersonalData() {
        return personalData;
    }

    public void setPersonalData(Set<PersonalDatum> personalData) {
        this.personalData = personalData;
    }

    public entities.Worker getWorker() {
        return worker;
    }

    public void setWorker(entities.Worker worker) {
        this.worker = worker;
    }

}