package ptithcm.device_maintenance.request;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.device_maintenance.helper.dto.ResponseDto;
import ptithcm.device_maintenance.request.dto.CompleteRequestDto;
import ptithcm.device_maintenance.request.dto.CreateRequestDto;
import ptithcm.device_maintenance.request.dto.UpdateRequestDto;
import ptithcm.device_maintenance.request.entity.Request;

import java.util.List;
import java.util.Optional;

@RequestMapping("${resource.prefix}/${resource.version}/${resource.module.request.name}")
@RestController
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<ResponseDto<Request>> createRequest(
            @RequestBody @NotNull CreateRequestDto payload
    ) throws BadRequestException {
        return ResponseEntity.ok(ResponseDto.<Request>builder()
                .data(requestService.save(payload))
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<Request>>> findAllRequests() {
        return ResponseEntity.ok(ResponseDto.<List<Request>>builder()
                .data(requestService.findAll())
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Request>> findById(
            @PathVariable @NotNull int id
    ) throws BadRequestException {
        Optional<Request> searchingRequest = requestService.findById(id);
        if (searchingRequest.isEmpty()) {
            throw new BadRequestException("Request not found");
        }

        return ResponseEntity.ok(ResponseDto.<Request>builder()
                .data(searchingRequest.get())
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }

    @PatchMapping
    public ResponseEntity<ResponseDto<Request>> updateRequest(
            @RequestBody @NotNull UpdateRequestDto payload
    ) throws BadRequestException {
        return ResponseEntity.ok(ResponseDto.<Request>builder()
                .data(requestService.update(payload))
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deleteRequest(
            @PathVariable @NotNull int id
    ) throws BadRequestException {
        requestService.delete(id);
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .build());
    }
}
