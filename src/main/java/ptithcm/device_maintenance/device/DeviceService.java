package ptithcm.device_maintenance.device;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ptithcm.device_maintenance.device.dto.CreateDeviceDto;
import ptithcm.device_maintenance.device.dto.UpdateDeviceDto;
import ptithcm.device_maintenance.helper.DateHelper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

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
                .purchaseDate(parsedPurchaseDate.get())
                .warrantyExpiryDate(parsedWarrantyExpiryDate.get())
                .deviceStatus(payload.getDeviceStatus())
                .build();
        return deviceRepository.save(newDevice);
    }

    public Device updateDevice(UpdateDeviceDto payload) throws BadRequestException {
        Optional<Device> updatingDevice = deviceRepository.findById(payload.getId());
        if (updatingDevice.isEmpty()) {
            throw new BadRequestException("Device not found");
        }

        var parsedPurchaseDate = DateHelper.parseStringAsLocalDate(payload.getPurchaseDate());
        if (parsedPurchaseDate.isEmpty()) {
            throw new BadRequestException("Invalid purchase date format");
        }

        var parsedWarrantyExpiryDate = DateHelper.parseStringAsLocalDate(payload.getWarrantyExpiryDate());
        if (parsedWarrantyExpiryDate.isEmpty()) {
            throw new BadRequestException("Invalid warranty expiry date format");
        }

        Device updatedDevice = Device.builder()
                .serialNumber(payload.getSerialNumber())
                .deviceName(payload.getDeviceName())
                .purchaseDate(parsedPurchaseDate.get())
                .warrantyExpiryDate(parsedWarrantyExpiryDate.get())
                .deviceStatus(payload.getDeviceStatus())
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return deviceRepository.save(updatedDevice);
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
