package ptithcm.device_maintenance.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Optional<Employee> findById(Integer id) {
        return employeeRepository.findById(id);
    }
}
