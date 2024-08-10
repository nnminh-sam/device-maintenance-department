package ptithcm.device_maintenance.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteRequestDto {
    private String id;

    private String completeDate;

    private String afterDescription;
}
