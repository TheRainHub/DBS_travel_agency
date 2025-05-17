package entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"Workers\"")
@PrimaryKeyJoinColumn(name = "user_id")
public class Worker extends User {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    private Long experience;
    private Long salary;

    @ManyToMany
    @JoinTable(name = "\"Worker_language\"", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<Language> languages = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "\"Tour_Workers\"", joinColumns = @JoinColumn(name = "\"user_id\""), inverseJoinColumns = @JoinColumn(name = "\"tour_id\""))
    private Set<Tour> tours = new LinkedHashSet<>();

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