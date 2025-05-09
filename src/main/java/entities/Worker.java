package entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"Workers\"")
public class Worker {
    @Id
    @Column(name = "worker_id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "position_id", nullable = false)
    private entities.Position position;

    @Column(name = "experience")
    private Long experience;

    @Column(name = "salary")
    private Long salary;

    @ManyToMany(mappedBy = "workers")
    private Set<entities.Position> positions = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "workers")
    private Set<Tour> tours = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "workers")
    private Set<entities.Language> languages = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public entities.Position getPosition() {
        return position;
    }

    public void setPosition(entities.Position position) {
        this.position = position;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Set<entities.Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<entities.Position> positions) {
        this.positions = positions;
    }

    public Set<Tour> getTours() {
        return tours;
    }

    public void setTours(Set<Tour> tours) {
        this.tours = tours;
    }

    public Set<entities.Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<entities.Language> languages) {
        this.languages = languages;
    }

}