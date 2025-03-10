package ptithcm.device_maintenance.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeviceDto {
    private String id;

    private String serialNumber;

    private String deviceName;

    private String description;

    private String purchaseDate;

    private String warrantyExpiryDate;

    private String assigneeId;

    private String employeeId;

    private String roomId;

    private String assignedDate;

    private String deviceStatus;
}
