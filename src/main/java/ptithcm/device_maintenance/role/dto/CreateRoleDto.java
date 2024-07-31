package ptithcm.device_maintenance.role.dto;

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
public class CreateRoleDto {
    @NotNull(message = "Role name cannot be null")
    @NotBlank(message = "Role name cannot be blank")
    private String name;
}
