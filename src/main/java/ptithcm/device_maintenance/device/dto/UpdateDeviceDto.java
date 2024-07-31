package ptithcm.device_maintenance.device.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeviceDto {
    private int id;

    @NotNull(message = "Device serial number cannot be null")
    @NotBlank(message = "Device serial number cannot be blank")
    private String serialNumber;

    @NotNull(message = "Device name cannot be null")
    @NotBlank(message = "Device name cannot be blank")
    private String deviceName;

    @NotNull(message = "Device description cannot be null")
    @NotBlank(message = "Device description cannot be blank")
    private String description;

    @NotNull(message = "Purchase date cannot be null")
    private String purchaseDate;

    @NotNull(message = "Warranty expiry date cannot be null")
    private String warrantyExpiryDate;

    @NotNull(message = "Device status cannot be null")
    private String deviceStatus;
}
