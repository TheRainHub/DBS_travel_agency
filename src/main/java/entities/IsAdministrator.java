package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "\"Is_administrator\"")
public class IsAdministrator {
    @Id
    @Column(name = "admin_id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private entities.Worker workers;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "position_id", nullable = false)
    private entities.Position position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public entities.Worker getWorkers() {
        return workers;
    }

    public void setWorkers(entities.Worker workers) {
        this.workers = workers;
    }

    public entities.Position getPosition() {
        return position;
    }

    public void setPosition(entities.Position position) {
        this.position = position;
    }

}