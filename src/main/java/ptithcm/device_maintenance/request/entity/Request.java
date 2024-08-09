package ptithcm.device_maintenance.request.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ptithcm.device_maintenance.device.Device;
import ptithcm.device_maintenance.employee.Employee;
import ptithcm.device_maintenance.helper.BaseEntity;
import ptithcm.device_maintenance.room.Room;

import java.time.LocalDate;

@Entity
@Table(name = "requests")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Request extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "request_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Column(name = "description_before", nullable = false)
    private String beforeDescription;

    @Column(name = "description_after")
    private String afterDescription;

    @Column(nullable = false)
    private RequestStatus status;

    @Column(name = "complete_date")
    private LocalDate completedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requested_by")
    private Employee requestBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Device device;
}
