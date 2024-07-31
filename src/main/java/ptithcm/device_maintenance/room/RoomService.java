package ptithcm.device_maintenance.room;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ptithcm.device_maintenance.room.dto.CreateRoomDto;
import ptithcm.device_maintenance.room.dto.UpdateRoomDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Room createRoom(CreateRoomDto payload) {
        Room newRoom = Room.builder().name(payload.getName()).build();
        return roomRepository.save(newRoom);
    }

    public List<Room> findAll() {
        return roomRepository.findAllByDeletedAtIsNull();
    }

    public Optional<Room> findById(int id) {
        return roomRepository.findById(id);
    }

    public Room updateRoom(
            UpdateRoomDto payload
    ) throws BadRequestException {
        Optional<Room> room = roomRepository.findById(payload.getId());
        if (room.isEmpty()) {
            throw new BadRequestException("Room not found");
        }

        Room updatingRoom = room.get();
        if (payload.getName().equals(updatingRoom.getName())) {
            throw new BadRequestException("Room name already exists");
        }

        updatingRoom.setName(payload.getName());
        updatingRoom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return roomRepository.save(updatingRoom);
    }

    public void deleteRoom(int id) throws BadRequestException {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            throw new BadRequestException("Room not found");
        }
        Room deletingRoom = room.get();
        deletingRoom.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        Room deletedRoom = roomRepository.save(deletingRoom);
    }
}
