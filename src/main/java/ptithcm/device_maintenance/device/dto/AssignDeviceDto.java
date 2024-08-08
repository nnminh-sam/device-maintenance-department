package ptithcm.device_maintenance.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignDeviceDto {
    private String deviceId;

    private String assigneeId;

    private String employeeId;

    private String roomId;

    private String assignDate;

    private String deviceStatus;
}
