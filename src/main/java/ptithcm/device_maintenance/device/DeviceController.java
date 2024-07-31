package ptithcm.device_maintenance.device;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.device_maintenance.device.dto.CreateDeviceDto;
import ptithcm.device_maintenance.device.dto.UpdateDeviceDto;
import ptithcm.device_maintenance.helper.dto.ResponseDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${resource.prefix}/${resource.version}/${resource.module.device.name}")
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<Device>>> findAllDevices() {
        return ResponseEntity.ok(ResponseDto.<List<Device>>builder()
                .data(deviceService.findAllDevices())
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Device>> findDeviceById(
            @PathVariable("id") int id
    ) throws BadRequestException {
        Optional<Device> searchingDevice = deviceService.findById(id);
        if (searchingDevice.isEmpty()) {
            throw new BadRequestException("Device not found");
        }

        return ResponseEntity.ok(ResponseDto.<Device>builder()
                .data(searchingDevice.get())
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }

    @PutMapping
    public ResponseEntity<ResponseDto<Device>> createDevice(
            @NotNull @RequestBody CreateDeviceDto payload
    ) {
        return ResponseEntity.ok(ResponseDto.<Device>builder()
                .data(deviceService.createDevice(payload))
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }

    @PatchMapping
    public ResponseEntity<ResponseDto<Device>> updateDevice(
            @NotNull @RequestBody UpdateDeviceDto payload
    ) throws BadRequestException {
        return ResponseEntity.ok(ResponseDto.<Device>builder()
                .data(deviceService.updateDevice(payload))
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deleteDevice(
            @PathVariable("id") int id
    ) throws BadRequestException {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }
}
