package ptithcm.device_maintenance.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ptithcm.device_maintenance.employee.Employee;
import ptithcm.device_maintenance.employee.Gender;
import ptithcm.device_maintenance.helper.DateHelper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotNull(message = "Second name cannot be null")
    @NotBlank(message = "Second name cannot be blank")
    private String lastName;

    @NotNull(message = "Citizen ID cannot be null")
    @NotBlank(message = "Citizen ID cannot be blank")
    private String citizenId;

    @NotNull(message = "Date of birth cannot be null")
    private String dateOfBirth;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be null")
    private String phone;

    private String address;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    public Employee toEmployee(PasswordEncoder passwordEncoder) throws BadRequestException {
        try {
            return Employee.builder()
                    .firstName(this.firstName)
                    .lastName(this.lastName)
                    .citizenId(this.citizenId)
                    .dateOfBirth(DateHelper.parseStringAsLocalDate(this.dateOfBirth))
                    .gender(this.gender)
                    .phone(this.phone)
                    .address(this.address)
                    .email(this.email)
                    .password(passwordEncoder.encode(this.password))
                    .build();
        } catch (Exception e) {
            throw new BadRequestException("Invalid date of birth format!");
        }
    }
}
