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
import ptithcm.device_maintenance.room.Room;
import ptithcm.device_maintenance.room.RoomService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    private final DeviceService deviceService;

    private final RoomService roomService;

    private final EmployeeService employeeService;

    public Request save(CreateRequestDto payload) throws BadRequestException {
        var requestBuilder = Request.builder();

        Optional<Device> selectingDevice = deviceService.findById(Integer.parseInt(payload.getDeviceId()));
        if (selectingDevice.isEmpty()) {
            throw new BadRequestException("Device not found");
        }
        requestBuilder.device(selectingDevice.get());

        Optional<Employee> requestEmployee = employeeService.findById(Integer.parseInt(payload.getRequestBy()));
        if (requestEmployee.isEmpty()) {
            throw new BadRequestException("Request employee not found");
        }
        requestBuilder.requestBy(requestEmployee.get());

        if (payload.getEmployeeId() != null) {
            Optional<Employee> employee = employeeService.findById(Integer.parseInt(payload.getEmployeeId()));
            if (employee.isEmpty()) {
                throw new BadRequestException("Employee not found");
            }
            requestBuilder.employee(employee.get());
        }

        if (payload.getRoomId() != null) {
            Optional<Room> room = roomService.findById(Integer.parseInt(payload.getRoomId()));
            if (room.isEmpty()) {
                throw new BadRequestException("Room not found");
            }
            requestBuilder.room(room.get());
        }

        return requestRepository.save(requestBuilder
                .requestType(RequestType.valueOf(payload.getRequestType()))
                .beforeDescription(payload.getBeforeDescription())
                .status(RequestStatus.PENDING)
                .build());
    }

    public Optional<Request> findById(int id) {
        return requestRepository.findById(id);
    }

    public List<Request> findAll() {
        return requestRepository.findByDeletedAtIsNull();
    }

    public Request update(@NotNull UpdateRequestDto payload) throws BadRequestException {
        Optional<Request> updatingRequest = requestRepository.findById(Integer.valueOf(payload.getId()));
        if (updatingRequest.isEmpty()) {
            throw new BadRequestException("Request not found");
        }
        Request updatedRequest = updatingRequest.get();

        var parsedCompletedDate = DateHelper.parseStringAsLocalDate(payload.getCompleteDate());
        if (parsedCompletedDate.isEmpty()) {
            throw new BadRequestException("Invalid complete date format");
        }
        updatedRequest.setCompletedDate(parsedCompletedDate.get());

        updatedRequest.setRequestType(RequestType.valueOf(payload.getRequestType()));
        updatedRequest.setBeforeDescription(payload.getBeforeDescription());
        updatedRequest.setAfterDescription(payload.getAfterDescription());
        updatedRequest.setStatus(RequestStatus.valueOf(payload.getStatus()));

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
