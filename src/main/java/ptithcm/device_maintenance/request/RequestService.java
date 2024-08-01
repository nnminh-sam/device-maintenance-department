package ptithcm.device_maintenance.request;

import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ptithcm.device_maintenance.device.DeviceService;
import ptithcm.device_maintenance.employee.Employee;
import ptithcm.device_maintenance.employee.EmployeeService;
import ptithcm.device_maintenance.helper.DateHelper;
import ptithcm.device_maintenance.request.dto.CreateRequestDto;
import ptithcm.device_maintenance.request.dto.UpdateRequestDto;
import ptithcm.device_maintenance.request.entity.Request;
import ptithcm.device_maintenance.request.entity.RequestStatus;
import ptithcm.device_maintenance.request.entity.RequestType;

import ptithcm.device_maintenance.device.Device;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    private final DeviceService deviceService;

    private final EmployeeService employeeService;

    public Request save(@NotNull CreateRequestDto payload) throws BadRequestException {
        Optional<Device> selectingDevice = deviceService.findById(payload.getDeviceId());
        if (selectingDevice.isEmpty()) {
            throw new BadRequestException("Device not found");
        }

        Optional<Employee> selectingEmployee = employeeService.findById(payload.getEmployeeId());
        if (selectingEmployee.isEmpty()) {
            throw new BadRequestException("Employee not found");
        }

        Request request = Request.builder()
                .requestType(RequestType.valueOf(payload.getRequestType()))
                .beforeDescription(payload.getBeforeDescription())
                .device(selectingDevice.get())
                .employee(selectingEmployee.get())
                .build();
        return requestRepository.save(request);
    }

    public Optional<Request> findById(int id) {
        return requestRepository.findById(id);
    }

    public List<Request> findAll() {
        return requestRepository.findByDeletedAtIsNull();
    }

    public Request update(@NotNull UpdateRequestDto payload) throws BadRequestException {
        Optional<Request> updatingRequest = requestRepository.findById(payload.getId());
        if (updatingRequest.isEmpty()) {
            throw new BadRequestException("Request not found");
        }

        Request updatedRequest = updatingRequest.get();
        updatedRequest.setRequestType(RequestType.valueOf(payload.getRequestType()));
        updatedRequest.setBeforeDescription(payload.getBeforeDescription());
        updatedRequest.setAfterDescription(payload.getAfterDescription());
        updatedRequest.setStatus(RequestStatus.valueOf(payload.getStatus()));
        updatedRequest.setCompletedDate(DateHelper.parseStringAsLocalDate(payload.getCompleteDate()));
        return requestRepository.save(updatedRequest);
    }

    public void delete(int id) throws BadRequestException {
        Optional<Request> deletingRequest = requestRepository.findById(id);
        if (deletingRequest.isEmpty()) {
            throw new BadRequestException("Request not found");
        }

        Request deletedRequest = deletingRequest.get();
        deletedRequest.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        requestRepository.save(deletedRequest);
    }
}
