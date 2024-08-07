package ptithcm.device_maintenance.maintenanceLog;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ptithcm.device_maintenance.device.Device;
import ptithcm.device_maintenance.device.DeviceService;
import ptithcm.device_maintenance.employee.Employee;
import ptithcm.device_maintenance.employee.EmployeeService;
import ptithcm.device_maintenance.helper.DateHelper;
import ptithcm.device_maintenance.maintenanceLog.dto.CreateMaintenanceLogDto;
import ptithcm.device_maintenance.maintenanceLog.dto.UpdateMaintenanceLogDto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaintenanceLogService {
    private final MaintenanceLogRepository managementLogRepository;

    private final DeviceService deviceService;

    private final EmployeeService employeeService;

    public MaintenanceLog save(CreateMaintenanceLogDto payload) throws BadRequestException {
        Optional<Device> selectingDevice = deviceService.findById(payload.getDeviceId());
        if (selectingDevice.isEmpty()) {
            throw new BadRequestException("Device not found");
        }

        Optional<Employee> selectingEmployee = employeeService.findById(payload.getEmployeeId());
        if (selectingEmployee.isEmpty()) {
            throw new BadRequestException("Employee not found");
        }

        MaintenanceLog maintenanceLog = MaintenanceLog.builder()
                .description(payload.getDescription())
                .employee(selectingEmployee.get())
                .device(selectingDevice.get())
                .build();
        return managementLogRepository.save(maintenanceLog);
    }

    public List<MaintenanceLog> findAll() {
        return managementLogRepository.findAllMaintenanceLogsByDeletedAtIsNull();
    }

    public Optional<MaintenanceLog> findById(int id) {
        return managementLogRepository.findById(id);
    }

    public MaintenanceLog update(UpdateMaintenanceLogDto payload) throws BadRequestException {
        Optional<MaintenanceLog> updatingMaintenanceLog = managementLogRepository.findById(payload.getId());
        if (updatingMaintenanceLog.isEmpty()) {
            throw new BadRequestException("Maintenance log not found");
        }

        Optional<LocalDate> parsedCompleteDate = DateHelper.parseStringAsLocalDate(payload.getCompleteDate());
        if (parsedCompleteDate.isEmpty()) {
            throw new BadRequestException("Invalid complete date format");
        }

        MaintenanceLog updatedMaintenanceLog = updatingMaintenanceLog.get();
        updatedMaintenanceLog.setDescription(payload.getDescription());
        updatedMaintenanceLog.setCompleteDate(parsedCompleteDate.get());
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
