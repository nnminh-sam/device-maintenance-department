package ptithcm.device_maintenance.role;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.device_maintenance.helper.dto.ResponseDto;
import ptithcm.device_maintenance.role.dto.CreateRoleDto;

import java.util.List;

@RestController
@RequestMapping("${resource.prefix}/${resource.version}/${resource.module.role.name}")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<Role>>> getAllRole() {
        return ResponseEntity.ok(ResponseDto.<List<Role>>builder()
                .data(roleService.getAllRoles())
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Role>> createRole(
            @RequestBody CreateRoleDto payload
    ) {
        return ResponseEntity.ok(ResponseDto.<Role>builder()
                .data(roleService.createRole(payload.getName()))
                .status(HttpStatus.CREATED)
                .message("Success")
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deleteRole(
            @PathVariable String id
    ) throws BadRequestException {
        this.roleService.deleteRole(Integer.parseInt(id));
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }
}
