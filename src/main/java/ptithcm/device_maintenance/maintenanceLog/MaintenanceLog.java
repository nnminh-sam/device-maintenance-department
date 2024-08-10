package ptithcm.device_maintenance.maintenanceLog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ptithcm.device_maintenance.employee.Employee;
import ptithcm.device_maintenance.helper.BaseEntity;
import ptithcm.device_maintenance.request.entity.Request;

@Entity
@Table(name = "maintenance_logs", uniqueConstraints = {
        @UniqueConstraint(
                name = "maintenance_logs_device_id_employee_id_unique",
                columnNames = {"request_id", "employee_id"}
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
