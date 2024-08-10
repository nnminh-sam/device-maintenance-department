package ptithcm.device_maintenance.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintainRequestDto {
    private String requestId;

    private String maintainById;
}
