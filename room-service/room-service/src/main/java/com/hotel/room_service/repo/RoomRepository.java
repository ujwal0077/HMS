package com.hotel.room_service.repo;

//package com.roomservice.repository;

import com.hotel.room_service.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
