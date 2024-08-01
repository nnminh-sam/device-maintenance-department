package ptithcm.device_maintenance.request.dto;

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
public class UpdateRequestDto {
    @NotNull(message = "Request's ID cannot be null")
    @NotBlank(message = "Request's ID cannot be blank")
    private int id;

    private String RequestType;

    private String beforeDescription;

    private String afterDescription;

    private String status;

    private String completeDate;
}
