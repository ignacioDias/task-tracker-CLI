package task;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class Task {

    private final long id;
    private static final AtomicLong counter = new AtomicLong();
    private String description;
    private Status status;
    private final String createdAt;
    private String updatedAt = null;

    public Task(String description) {
        this.id = counter.incrementAndGet();
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = LocalTime.now().toString();
    }
    public Task() {
        this.id = -1; // Gson lo sobreescribirá al deserializar
        this.description = "";
        this.status = Status.IN_PROGRESS;
        this.createdAt = LocalTime.now().toString();
    }

    public static void setCounter(long value) {
        counter.set(value);
    }

    public long getId() {
        return id;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Status getStatus() {
        return status;
    }
    public void setDescription(String description) {
        this.description = description;
        this.setUpdatedAt();
    }
    public String getDescription() {
        return description;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt() {
        this.updatedAt = LocalTime.now().toString();
    }
}