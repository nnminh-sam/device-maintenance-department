package ptithcm.device_maintenance.employee.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ptithcm.device_maintenance.employee.Gender;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeDto {
    private String firstName;

    private String lastName;

    private Gender gender;

    private String phone;

    private String dateOfBirth;

    private String address;
}
