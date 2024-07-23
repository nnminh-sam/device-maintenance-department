package ptithcm.device_maintenance.helper;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;

    @Column(name = "deleted_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp deletedAt;

    @PrePersist
    public void prePersist() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
