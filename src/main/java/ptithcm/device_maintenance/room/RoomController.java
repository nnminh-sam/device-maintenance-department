package ptithcm.device_maintenance.room;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.device_maintenance.helper.dto.ResponseDto;
import ptithcm.device_maintenance.room.dto.CreateRoomDto;
import ptithcm.device_maintenance.room.dto.UpdateRoomDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${resource.prefix}/${resource.version}/${resource.module.room.name}")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("all")
    public ResponseEntity<ResponseDto<List<Room>>> getAllRooms() {
        return ResponseEntity.ok(ResponseDto.<List<Room>>builder()
                .data(roomService.findAll())
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Room>> getRoomById(
            @PathVariable int id
    ) throws BadRequestException {
        Optional<Room> searchingRoom = roomService.findById(id);
        if (searchingRoom.isEmpty()) {
            throw new BadRequestException("Room not found");
        }
        return ResponseEntity.ok(ResponseDto.<Room>builder()
                .data(searchingRoom.get())
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }

    @PostMapping()
    public ResponseEntity<ResponseDto<Room>> createRoom(
            @RequestBody CreateRoomDto payload
    ) {
        return ResponseEntity.ok(ResponseDto.<Room>builder()
                .data(roomService.createRoom(payload))
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }

    @PatchMapping()
    public ResponseEntity<ResponseDto<Room>> updateRoom(
            @RequestBody UpdateRoomDto payload
    ) throws BadRequestException {
        return ResponseEntity.ok(ResponseDto.<Room>builder()
                .data(roomService.updateRoom(payload))
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deleteRoom(
            @PathVariable int id
    ) throws BadRequestException {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .status(HttpStatus.OK)
                .message("Success")
                .build()
        );
    }
}
