package ptithcm.device_maintenance.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ptithcm.device_maintenance.helper.dto.ResponseDto;

@RequestMapping("${resource.prefix}/${resource.version}/${resource.module.employee.name}")
@RestController
@RequiredArgsConstructor
public class EmployeeController {
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
}
