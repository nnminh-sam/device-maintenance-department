package ptithcm.device_maintenance.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r FROM Room AS r WHERE r.deletedAt IS NULL")
    List<Room> findAllByDeletedAtIsNull();

    @Query("SELECT r FROM Room AS r WHERE r.deletedAt IS NULL AND r.id = :id")
    Optional<Room> findById(@Param("id") int id);
}
