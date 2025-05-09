package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class WorkerLanguageId implements java.io.Serializable {
    private static final long serialVersionUID = 8380113643766879469L;
    @Column(name = "worker_id", nullable = false)
    private Long workerId;

    @Column(name = "language_id", nullable = false)
    private String languageId;

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WorkerLanguageId entity = (WorkerLanguageId) o;
        return Objects.equals(this.workerId, entity.workerId) &&
                Objects.equals(this.languageId, entity.languageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workerId, languageId);
    }

}