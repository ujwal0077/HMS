package com.hotel.room_service.service;

//package com.roomservice.service;

import com.hotel.room_service.model.Room;
import com.hotel.room_service.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.setRoomNumber(updatedRoom.getRoomNumber());
            room.setRoomType(updatedRoom.getRoomType());
            room.setRate(updatedRoom.getRate());
            room.setStatus(updatedRoom.getStatus()); 
            return roomRepository.save(room);
        }
        return null;
    }


    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Room updateRate(Long id, Double newRate) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.setRate(newRate);
            return roomRepository.save(room);
        }
        return null;
    }
    
}
