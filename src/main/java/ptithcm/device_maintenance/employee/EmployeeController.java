package ptithcm.device_maintenance.employee;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ptithcm.device_maintenance.employee.dtos.UpdateEmployeeDto;
import ptithcm.device_maintenance.exception.UnauthorizedException;
import ptithcm.device_maintenance.helper.dto.ResponseDto;

@RequestMapping("${resource.prefix}/${resource.version}/${resource.module.employee.name}")
@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/my")
    public ResponseEntity<ResponseDto<Employee>> getEmployeeInformation() {
        var context = SecurityContextHolder.getContext().getAuthentication();
        var employee = (Employee) context.getPrincipal();
        return ResponseEntity.ok(ResponseDto.<Employee>builder()
                .data(employee)
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Employee>> updateEmployee(
            @PathVariable int id,
            @RequestBody UpdateEmployeeDto payload
            ) throws BadRequestException, UnauthorizedException {
        var context = SecurityContextHolder.getContext().getAuthentication();
        var employee = (Employee) context.getPrincipal();
        if (!employee.getId().equals(id)) {
            throw new UnauthorizedException("Unauthorized user!");
        }
        return ResponseEntity.ok(ResponseDto.<Employee>builder()
                .data(this.employeeService.update(id, payload))
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }
}
