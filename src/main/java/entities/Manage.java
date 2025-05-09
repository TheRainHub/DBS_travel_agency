package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "\"Manages\"")
public class Manage {
    @EmbeddedId
    private ManageId id;

    @MapsId("workerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "worker_id", nullable = false)
    private entities.Worker worker;

    @MapsId("tourId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_id", nullable = false)
    private entities.Tour tour;

    public ManageId getId() {
        return id;
    }

    public void setId(ManageId id) {
        this.id = id;
    }

    public entities.Worker getWorker() {
        return worker;
    }

    public void setWorker(entities.Worker worker) {
        this.worker = worker;
    }

    public entities.Tour getTour() {
        return tour;
    }

    public void setTour(entities.Tour tour) {
        this.tour = tour;
    }

}