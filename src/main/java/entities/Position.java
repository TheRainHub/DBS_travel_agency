package entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"position\"")
public class Position {
    @Id
    @Column(name = "position_id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "position")
    private Set<Worker> workers = new LinkedHashSet<>();
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }

    public void setName(String name) {
        this.name = name;
    }
}