package ptithcm.device_maintenance.device;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ptithcm.device_maintenance.employee.Employee;
import ptithcm.device_maintenance.helper.BaseEntity;
import ptithcm.device_maintenance.room.Room;

import java.time.LocalDate;

@Entity
@Table(name = "devices")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Device extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @Column(nullable = false)
    private String description;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "assignedDate")
    private LocalDate assignedDate;

    @Column(name = "warranty_expiry_date", nullable = false)
    private LocalDate warrantyExpiryDate;

    @Column(name = "device_status")
    private String deviceStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
