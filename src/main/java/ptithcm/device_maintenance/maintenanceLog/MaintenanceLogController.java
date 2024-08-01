package ptithcm.device_maintenance.maintenanceLog;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.device_maintenance.helper.dto.ResponseDto;
import ptithcm.device_maintenance.maintenanceLog.dto.CreateMaintenanceLogDto;
import ptithcm.device_maintenance.maintenanceLog.dto.UpdateMaintenanceLogDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${resource.prefix}/${resource.version}/${resource.module.maintenanceLog.name}")
public class MaintenanceLogController {
    private final MaintenanceLogService maintenanceLogService;

    @PostMapping
    public ResponseEntity<ResponseDto<MaintenanceLog>> createMaintenanceLog(
            @NotNull @RequestBody CreateMaintenanceLogDto payload
    ) throws BadRequestException {
        return ResponseEntity.ok(ResponseDto.<MaintenanceLog>builder()
                .data(maintenanceLogService.save(payload))
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<MaintenanceLog>>> findAllMaintenanceLogs() {
        return ResponseEntity.ok(ResponseDto.<List<MaintenanceLog>>builder()
                .data(maintenanceLogService.findAll())
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MaintenanceLog>> findMaintenanceLogById(
            @PathVariable int id
    ) throws BadRequestException {
        Optional<MaintenanceLog> searchingMaintenanceLog = maintenanceLogService.findById(id);
        if (searchingMaintenanceLog.isEmpty()) {
            throw new BadRequestException("Maintenance log not found");
        }

        return ResponseEntity.ok(ResponseDto.<MaintenanceLog>builder()
                .data(searchingMaintenanceLog.get())
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }

    @PatchMapping
    public ResponseEntity<ResponseDto<MaintenanceLog>> updateMaintenanceLog(
            @NotNull @RequestBody UpdateMaintenanceLogDto payload
    ) throws BadRequestException {
        return ResponseEntity.ok(ResponseDto.<MaintenanceLog>builder()
                .data(maintenanceLogService.update(payload))
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deleteMaintenanceLogById(
            @PathVariable int id
    ) throws BadRequestException {
        maintenanceLogService.delete(id);
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }
}
