package entities;

import jakarta.persistence.*;

@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // или другой способ генерации
    private Long id;

    private String message;

    private String level;

    public String getMessage() {
        return message;
    }

    public String getLevel() {
        return level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
