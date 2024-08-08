package ptithcm.device_maintenance.employee;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ptithcm.device_maintenance.employee.dtos.UpdateEmployeeDto;
import ptithcm.device_maintenance.helper.DateHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }

        return employeeRepository.findById(id);
    }

    public Employee update(int id, UpdateEmployeeDto payload) throws BadRequestException {
        Optional<Employee> updatingEmployee = employeeRepository.findById(id);
        if (updatingEmployee.isEmpty()) {
            throw new BadRequestException("Invalid employee ID");
        }

        Optional<LocalDate> parsedDob = DateHelper.parseStringAsLocalDate(payload.getDateOfBirth());
        if (parsedDob.isEmpty()) {
            throw new BadRequestException("Invalid date of birth format");
        }

        Employee updatedEmployee = updatingEmployee.get();
        updatedEmployee.setFirstName(payload.getFirstName());
        updatedEmployee.setLastName(payload.getLastName());
        updatedEmployee.setGender(payload.getGender());
        updatedEmployee.setAddress(payload.getAddress());
        updatedEmployee.setDateOfBirth(parsedDob.get());
        return employeeRepository.save(updatedEmployee);
    }
}
