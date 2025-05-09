package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "\"Worker_language\"")
public class WorkerLanguage {
    @EmbeddedId
    private WorkerLanguageId id;

    @MapsId("workerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "worker_id", nullable = false)
    private entities.Worker worker;

    @MapsId("languageId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private entities.Language language;

    public WorkerLanguageId getId() {
        return id;
    }

    public void setId(WorkerLanguageId id) {
        this.id = id;
    }

    public entities.Worker getWorker() {
        return worker;
    }

    public void setWorker(entities.Worker worker) {
        this.worker = worker;
    }

    public entities.Language getLanguage() {
        return language;
    }

    public void setLanguage(entities.Language language) {
        this.language = language;
    }

}