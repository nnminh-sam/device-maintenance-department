package ptithcm.device_maintenance.request.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequestDto {
    private String requestType;

    private String beforeDescription;

    private String employeeId;

    private String roomId;

    private String requestBy;

    private String deviceId;
}
