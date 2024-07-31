package ptithcm.device_maintenance.auth;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.device_maintenance.auth.dto.AuthenticateRequest;
import ptithcm.device_maintenance.auth.dto.AuthenticationResponse;
import ptithcm.device_maintenance.auth.dto.RegisterRequest;
import ptithcm.device_maintenance.helper.dto.ResponseDto;

@RestController
@RequestMapping("${resource.prefix}/${resource.version}/${resource.module.auth.name}")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<AuthenticationResponse>> register(
            @RequestBody RegisterRequest payload
    ) throws BadRequestException {
        return ResponseEntity.ok(ResponseDto.<AuthenticationResponse>builder()
                .data(authenticationService.register(payload))
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDto<AuthenticationResponse>> authenticate(
            @RequestBody AuthenticateRequest payload
    ) {
        return ResponseEntity.ok(ResponseDto.<AuthenticationResponse>builder()
                .data(authenticationService.authenticate(payload))
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }
}
