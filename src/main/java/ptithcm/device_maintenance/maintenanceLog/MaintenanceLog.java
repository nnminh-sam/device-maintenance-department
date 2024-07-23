package ptithcm.device_maintenance.maintenanceLog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ptithcm.device_maintenance.device.Device;
import ptithcm.device_maintenance.employee.Employee;
import ptithcm.device_maintenance.helper.BaseEntity;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance_logs", uniqueConstraints = {
        @UniqueConstraint(
                name = "maintenance_logs_device_id_employee_id_unique",
                columnNames = {"device_id", "employee_id"}
        )
})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class MaintenanceLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "complete_date", nullable = false)
    private LocalDate completeDate;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
