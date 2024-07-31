package ptithcm.device_maintenance.employee;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.device_maintenance.helper.dto.ResponseDto;

@RequestMapping("${resource.prefix}/${resource.version}/${resource.module.employee.name}")
@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

//    @GetMapping("")
//    public ResponseEntity<ResponseDto<List<Employee>>> findAllEmployee(
//            @RequestParam(name = "size") int size,
//            @RequestParam(name = "page") int page,
//            @RequestParam(name = "sort-by", defaultValue = "asc") String sortBy
//    ) {
//
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Employee>> findEmployeeById(
            @PathVariable Integer id
    ) throws BadRequestException {
        return ResponseEntity.ok(ResponseDto.<Employee>builder()
                .data(this.employeeService.findById(id))
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }
}
