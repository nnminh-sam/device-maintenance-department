package ptithcm.device_maintenance.maintenanceLog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMaintenanceLogDto {
    private String id;

    private String completeDate;

    private String afterDescription;
}
