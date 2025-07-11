package com.hotel.reserv_service.client;

import java.util.List;

//package com.reservation.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.hotel.reserv_service.dto.Room;

@FeignClient(name = "room-service")
public interface RoomClient {

    @GetMapping("/api/rooms/{id}")
    Room getRoomById(@PathVariable("id") Long id);

    @GetMapping("/api/rooms")
    List<Room> getAllRooms();

    @PutMapping("/api/rooms/{id}")
    void updateRoom(@PathVariable("id") Long id, @RequestBody Room room);
}


