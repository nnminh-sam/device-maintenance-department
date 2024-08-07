package ptithcm.device_maintenance.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ptithcm.device_maintenance.auth.dto.AuthenticateRequest;
import ptithcm.device_maintenance.auth.dto.AuthenticationResponse;
import ptithcm.device_maintenance.auth.dto.RegisterRequest;
import ptithcm.device_maintenance.configs.JwtService;
import ptithcm.device_maintenance.employee.Employee;
import ptithcm.device_maintenance.employee.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(
            @Valid RegisterRequest payload
    ) throws BadRequestException {
        var newEmployee = payload.toEmployee(passwordEncoder);
        Employee saved = employeeRepository.save(newEmployee);
        var jwtToken = jwtService.generateToken(saved);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticateRequest payload) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        payload.getEmail(),
                        payload.getPassword()
                )
        );
        var employee = employeeRepository.findByEmail(payload.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(employee);
        return new AuthenticationResponse(jwtToken);
    }
}
