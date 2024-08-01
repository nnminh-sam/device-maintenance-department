package ptithcm.device_maintenance.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.device_maintenance.request.entity.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query("SELECT r FROM Request AS r WHERE r.deletedAt IS NULL AND r.id = :id")
    Optional<Request> findById(@Param("id") int id);

    @Query("SELECT r FROM Request AS r WHERE r.deletedAt IS NULL")
    List<Request> findByDeletedAtIsNull();
}
