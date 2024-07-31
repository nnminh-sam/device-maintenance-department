package ptithcm.device_maintenance.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    @Query("SELECT d FROM Device AS d WHERE d.deletedAt IS NULL")
    List<Device> findAllByDeletedAtIsNull();

    @Query("SELECT d FROM Device AS d WHERE d.deletedAt IS NULL AND d.id = :id")
    Optional<Device> findById(@Param("id") int id);
}
