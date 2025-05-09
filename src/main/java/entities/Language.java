package entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "language")
public class Language {
    @Id
    @Column(name = "language_id", nullable = false, length = 255)
    private String id;
    private String languageId;


    @ManyToMany
    private Set<Worker> workers = new LinkedHashSet<>();

    public String getLanguageId() {
        return languageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }

}