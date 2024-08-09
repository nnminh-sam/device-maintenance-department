package ptithcm.device_maintenance.device;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ptithcm.device_maintenance.device.dto.AssignDeviceDto;
import ptithcm.device_maintenance.device.dto.CreateDeviceDto;
import ptithcm.device_maintenance.device.dto.UpdateDeviceDto;
import ptithcm.device_maintenance.employee.Employee;
import ptithcm.device_maintenance.employee.EmployeeService;
import ptithcm.device_maintenance.helper.DateHelper;
import ptithcm.device_maintenance.room.Room;
import ptithcm.device_maintenance.room.RoomService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    private final EmployeeService employeeService;

    private final RoomService roomService;

    public List<Device> findAllDevices() {
        return deviceRepository.findAllByDeletedAtIsNull();
    }

    public Optional<Device> findById(int id) {
        return deviceRepository.findById(id);
    }

    public Device createDevice(CreateDeviceDto payload) throws BadRequestException {
        var parsedPurchaseDate = DateHelper.parseStringAsLocalDate(payload.getPurchaseDate());
        if (parsedPurchaseDate.isEmpty()) {
            throw new BadRequestException("Invalid purchase date format");
        }

        var parsedWarrantyExpiryDate = DateHelper.parseStringAsLocalDate(payload.getWarrantyExpiryDate());
        if (parsedWarrantyExpiryDate.isEmpty()) {
            throw new BadRequestException("Invalid warranty expiry date format");
        }

        Device newDevice = Device.builder()
                .serialNumber(payload.getSerialNumber())
                .deviceName(payload.getDeviceName())
                .description(payload.getDescription())
                .purchaseDate(parsedPurchaseDate.get())
                .warrantyExpiryDate(parsedWarrantyExpiryDate.get())
                .deviceStatus(payload.getDeviceStatus())
                .build();
        try {
            return deviceRepository.save(newDevice);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public Device updateDevice(UpdateDeviceDto payload) throws BadRequestException {
        if (payload.getEmployeeId() != null && payload.getRoomId() != null) {
            throw new BadRequestException("Device cannot be assigned to two object");
        }

        Optional<Device> updatingDevice = deviceRepository.findById(Integer.parseInt(payload.getId()));
        if (updatingDevice.isEmpty()) {
            throw new BadRequestException("Device not found");
        }
        Device updatedDevice = updatingDevice.get();

        var parsedPurchaseDate = DateHelper.parseStringAsLocalDate(payload.getPurchaseDate());
        if (parsedPurchaseDate.isEmpty()) {
            throw new BadRequestException("Invalid purchase date format");
        }

        var parsedWarrantyExpiryDate = DateHelper.parseStringAsLocalDate(payload.getWarrantyExpiryDate());
        if (parsedWarrantyExpiryDate.isEmpty()) {
            throw new BadRequestException("Invalid warranty expiry date format");
        }

        if (payload.getAssignedDate() != null
                && (updatedDevice.getAssignedDate() == null
                || !updatedDevice.getAssignedDate().toString().equals(payload.getAssignedDate()))) {
            var parsedAssignedDate = DateHelper.parseStringAsLocalDate(payload.getAssignedDate());
            if (parsedAssignedDate.isEmpty()) {
                throw new BadRequestException("Invalid assignment date format");
            }
            updatedDevice.setAssignedDate(parsedAssignedDate.get());
        }

        if (payload.getSerialNumber() != null && !updatedDevice.getSerialNumber().equals(payload.getSerialNumber())) {
            updatedDevice.setSerialNumber(payload.getSerialNumber());
        }
        if (payload.getDeviceName() != null && !updatedDevice.getDeviceName().equals(payload.getDeviceName())) {
            updatedDevice.setDeviceName(payload.getDeviceName());
        }
        if (payload.getDescription() != null && !updatedDevice.getDescription().equals(payload.getDescription())) {
            updatedDevice.setDescription(payload.getDescription());
        }
        if (payload.getPurchaseDate() != null && !updatedDevice.getPurchaseDate().toString().equals(payload.getPurchaseDate())) {
            updatedDevice.setPurchaseDate(parsedPurchaseDate.get());
        }
        if (payload.getWarrantyExpiryDate() != null && !updatedDevice.getWarrantyExpiryDate().toString().equals(payload.getWarrantyExpiryDate())) {
            updatedDevice.setWarrantyExpiryDate(parsedWarrantyExpiryDate.get());
        }
        if (payload.getDeviceStatus() == null
                || updatedDevice.getDeviceStatus() == null
                || !updatedDevice.getDeviceStatus().equals(payload.getDeviceStatus())) {
            updatedDevice.setDeviceStatus(payload.getDeviceStatus());
        }
        if (payload.getEmployeeId() != null) {
            Optional<Employee> employee = employeeService.findById(Integer.parseInt(payload.getEmployeeId()));
            if (employee.isEmpty()) {
                throw new BadRequestException("Employee not found");
            }
            updatedDevice.setEmployee(employee.get());
            updatedDevice.setRoom(null);
        }
        else {
            updatedDevice.setEmployee(null);
        }

        if (payload.getAssigneeId() != null) {
            Optional<Employee> assignee = employeeService.findById(Integer.parseInt(payload.getAssigneeId()));
            if (assignee.isEmpty()) {
                throw new BadRequestException("Assignee not found");
            }
            updatedDevice.setAssignee(assignee.get());
        }
        if (payload.getRoomId() != null) {
            Optional<Room> room = roomService.findById(Integer.parseInt(payload.getRoomId()));
            if (room.isEmpty()) {
                throw new BadRequestException("Room not found");
            }
            updatedDevice.setRoom(room.get());
            updatedDevice.setEmployee(null);
        }
        else {
            updatedDevice.setRoom(null);
        }

        return deviceRepository.save(updatedDevice);
    }

    public Device assignDevice(AssignDeviceDto payload) throws BadRequestException {
        Optional<Device> updatingDevice = deviceRepository.findById(Integer.parseInt(payload.getDeviceId()));
        if (updatingDevice.isEmpty()) {
            throw new BadRequestException("Device not found");
        }
        Device updatedDevice = updatingDevice.get();

        Optional<Employee> assignee = employeeService.findById(Integer.valueOf(payload.getAssigneeId()));
        if (assignee.isEmpty()) {
            throw new BadRequestException("Assignee not found");
        }
        updatedDevice.setAssignee(assignee.get());

        if (payload.getRoomId() == null && payload.getEmployeeId() == null) {
            throw new BadRequestException("RoomId and EmployeeId not found");
        }

        if (payload.getEmployeeId() != null) {
            Optional<Employee> employee = employeeService.findById(Integer.valueOf(payload.getEmployeeId()));
            if (employee.isEmpty()) {
                throw new BadRequestException("Employee not found");
            }
            updatedDevice.setEmployee(employee.get());
        }

        if (payload.getRoomId() != null) {
            Optional<Room> room = roomService.findById(Integer.parseInt(payload.getRoomId()));
            if (room.isEmpty()) {
                throw new BadRequestException("Room not found");
            }
            updatedDevice.setRoom(room.get());
        }

        Optional<LocalDate> assignDate = DateHelper.parseStringAsLocalDate(payload.getAssignDate());
        if (assignDate.isEmpty()) {
            throw new BadRequestException("Invalid assignment date format");
        }

        updatedDevice.setAssignedDate(assignDate.get());
        updatedDevice.setDeviceStatus(payload.getDeviceStatus());

        try {
            return deviceRepository.save(updatedDevice);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public void deleteDevice(int id) throws BadRequestException {
        Optional<Device> updatingDevice = deviceRepository.findById(id);
        if (updatingDevice.isEmpty()) {
            throw new BadRequestException("Device not found");
        }
        Device deletedDevice = updatingDevice.get();
        deletedDevice.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        deviceRepository.save(deletedDevice);
    }
}
