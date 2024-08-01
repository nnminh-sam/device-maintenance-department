package ptithcm.device_maintenance.maintenanceLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Integer> {
    @Query("SELECT m FROM MaintenanceLog AS m WHERE m.deletedAt IS NULL")
    List<MaintenanceLog> findAllMaintenanceLogsByDeletedAtIsNull();

    @Query("SELECT m FROM MaintenanceLog AS m WHERE m.deletedAt IS NULL AND m.id = :id")
    Optional<MaintenanceLog> findById(@Param("id") int id);
}
