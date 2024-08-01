package ptithcm.device_maintenance.maintenanceLog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMaintenanceLogDto {
    private int deviceId;

    private int employeeId;

    private String description;
}
