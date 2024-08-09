package ptithcm.device_maintenance.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestDto {
    private String id;

    private String RequestType;

    private String beforeDescription;

    private String afterDescription;

    private String status;

    private String completeDate;

    private String employeeId;

    private String roomId;

    private String deviceId;
}
