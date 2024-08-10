package ptithcm.device_maintenance.maintenanceLog;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ptithcm.device_maintenance.employee.Employee;
import ptithcm.device_maintenance.employee.EmployeeService;
import ptithcm.device_maintenance.helper.DateHelper;
import ptithcm.device_maintenance.maintenanceLog.dto.CreateMaintenanceLogDto;
import ptithcm.device_maintenance.maintenanceLog.dto.UpdateMaintenanceLogDto;
import ptithcm.device_maintenance.request.RequestService;
import ptithcm.device_maintenance.request.dto.CompleteRequestDto;
import ptithcm.device_maintenance.request.entity.Request;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaintenanceLogService {
    private final MaintenanceLogRepository managementLogRepository;

    private final RequestService requestService;

    private final EmployeeService employeeService;

    public MaintenanceLog save(CreateMaintenanceLogDto payload) throws BadRequestException {
        var maintenanceLogBuilder = MaintenanceLog.builder();

        Optional<Request> selectingRequest = requestService.findById(Integer.parseInt(payload.getRequestId()));
        if (selectingRequest.isEmpty()) {
            throw new BadRequestException("Request not found");
        }
        maintenanceLogBuilder.request(selectingRequest.get());

        Optional<Employee> selectingEmployee = employeeService.findById(Integer.parseInt(payload.getEmployeeId()));
        if (selectingEmployee.isEmpty()) {
            throw new BadRequestException("Employee not found");
        }
        maintenanceLogBuilder.employee(selectingEmployee.get());

        return managementLogRepository.save(maintenanceLogBuilder.build());
    }

    public List<MaintenanceLog> findAll() {
        return managementLogRepository.findAllMaintenanceLogsByDeletedAtIsNull();
    }

    public Optional<MaintenanceLog> findById(int id) {
        return managementLogRepository.findById(id);
    }

    public MaintenanceLog update(UpdateMaintenanceLogDto payload) throws BadRequestException {
        Optional<MaintenanceLog> updatingMaintenanceLog = managementLogRepository.findById(Integer
                .parseInt(payload.getId()));
        if (updatingMaintenanceLog.isEmpty()) {
            throw new BadRequestException("Maintenance log not found");
        }
        MaintenanceLog updatedMaintenanceLog = updatingMaintenanceLog.get();

        var parsedCompleteDate = DateHelper.parseStringAsLocalDate(payload.getCompleteDate());
        if (parsedCompleteDate.isEmpty()) {
            throw new BadRequestException("Invalid complete date format");
        }

        requestService.completeRequest(CompleteRequestDto.builder()
                        .id(updatedMaintenanceLog.getRequest().getId().toString())
                        .afterDescription(payload.getAfterDescription())
                        .completeDate(payload.getCompleteDate())
                .build());

        updatedMaintenanceLog.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return managementLogRepository.save(updatedMaintenanceLog);
    }

    public void delete(int id) throws BadRequestException {
        Optional<MaintenanceLog> deletingMaintenanceLog = managementLogRepository.findById(id);
        if (deletingMaintenanceLog.isEmpty()) {
            throw new BadRequestException("Maintenance log not found");
        }

        MaintenanceLog deletedMaintenanceLog = deletingMaintenanceLog.get();
        deletedMaintenanceLog.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        managementLogRepository.save(deletedMaintenanceLog);
    }
}
