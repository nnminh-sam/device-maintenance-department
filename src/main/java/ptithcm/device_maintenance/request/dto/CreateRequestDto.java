package ptithcm.device_maintenance.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequestDto {
    @NotNull(message = "Request type must not be null")
    @NotBlank(message = "Request type must not be blank")
    private String RequestType;

    @NotNull(message = "Device's before description must not be null")
    @NotBlank(message = "Device's before description must not be blank")
    private String beforeDescription;

    @NotNull(message = "Employee's ID must not be null")
    @NotBlank(message = "Employee's ID must not be blank")
    private int employeeId;

    @NotNull(message = "Device's ID must not be null")
    @NotBlank(message = "Device's ID must not be blank")
    private int deviceId;
}
