package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class ManageId implements java.io.Serializable {
    private static final long serialVersionUID = -609699314945831787L;
    @Column(name = "worker_id", nullable = false)
    private Long workerId;

    @Column(name = "tour_id", nullable = false)
    private Long tourId;

    public Long getworkerId() {
        return workerId;
    }

    public void setworkerId(Long workerId) {
        this.workerId = workerId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ManageId entity = (ManageId) o;
        return Objects.equals(this.workerId, entity.workerId) &&
                Objects.equals(this.tourId, entity.tourId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workerId, tourId);
    }

}