package ptithcm.device_maintenance.helper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResponseDto <T> {
    private T data;

    private HttpStatus status;

    private String message;
}
