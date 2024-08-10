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

    private String requestType;

    private String beforeDescription;

    private String afterDescription;

    private String status;

    private String completeDate;
}
