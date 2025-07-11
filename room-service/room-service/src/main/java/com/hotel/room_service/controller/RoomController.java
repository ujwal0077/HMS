package com.hotel.room_service.controller;

//package com.roomservice.controller;

import com.hotel.room_service.model.Room;


import com.hotel.room_service.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api/rooms")
//public class RoomController {
//
//    @Autowired
//    private RoomService roomService;
//
//    @GetMapping
//    public List<Room> getAllRooms() {
//        return roomService.getAllRooms();
//    }
//
//    @PostMapping
//    public Room createRoom(@RequestBody Room room) {
//        return roomService.createRoom(room);
//    }
//
//    @PutMapping("/{id}")
//    public Room updateRoom(@PathVariable Long id, @RequestBody Room room) {
//        return roomService.updateRoom(id, room);
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteRoom(@PathVariable Long id) {
//        boolean isDeleted = roomService.deleteRoom(id);
//        return isDeleted ? "Room deleted successfully" : "Room not found";
//    }
//
//    @PutMapping("/{id}/rate")
//    public Room updateRoomRate(@PathVariable Long id, @RequestParam Double rate) {
//        return roomService.updateRate(id, rate);
//    }
//}
//package com.hotel.room_service.controller;

import com.hotel.room_service.model.Room; 
import com.hotel.room_service.service.RoomService; 
import io.swagger.v3.oas.annotations.Operation; 
import io.swagger.v3.oas.annotations.responses.ApiResponse; 
import io.swagger.v3.oas.annotations.responses.ApiResponses; 
import io.swagger.v3.oas.annotations.tags.Tag; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Room Controller", description = "APIs for managing hotel room operations") @RestController @RequestMapping("/api/rooms") 
public class RoomController 
{
	@Autowired
	private RoomService roomService;

	@Operation(summary = "Get all rooms", description = "Fetch all available rooms in the hotel")
	@ApiResponse(responseCode = "200", description = "Rooms retrieved successfully")
	@GetMapping
	public List<Room> getAllRooms() {
	    return roomService.getAllRooms();
	}
	
	@Operation(summary = "Get room by ID", description = "Fetch room details by its ID")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Room found successfully"),
	    @ApiResponse(responseCode = "404", description = "Room not found")
	})
	@GetMapping("/{id}")
	public Room getRoomById(@PathVariable Long id) {
	    return roomService.getRoomById(id);
	}

	
	
	@Operation(summary = "Create a new room", description = "Create and save a new hotel room")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Room created successfully"),
	    @ApiResponse(responseCode = "400", description = "Invalid input")
	})
	@PostMapping
	public Room createRoom(@RequestBody Room room) {
	    return roomService.createRoom(room);
	}

	@Operation(summary = "Update room by ID", description = "Update details of a room based on its ID")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Room updated successfully"),
	    @ApiResponse(responseCode = "404", description = "Room not found")
	})
	@PutMapping("/{id}")
	public Room updateRoom(@PathVariable Long id, @RequestBody Room room) {
	    return roomService.updateRoom(id, room);
	}

	@Operation(summary = "Delete a room", description = "Delete a room based on its ID")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Room deleted successfully"),
	    @ApiResponse(responseCode = "404", description = "Room not found")
	})
	@DeleteMapping("/{id}")
	public String deleteRoom(@PathVariable Long id) {
	    boolean isDeleted = roomService.deleteRoom(id);
	    return isDeleted ? "Room deleted successfully" : "Room not found";
	}

	@Operation(summary = "Update room rate", description = "Update the nightly rate of a room")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Rate updated successfully"),
	    @ApiResponse(responseCode = "404", description = "Room not found")
	})
	@PutMapping("/{id}/rate")
	public Room updateRoomRate(@PathVariable Long id, @RequestParam Double rate) {
	    return roomService.updateRate(id, rate);
	}
	
	
	
	
	}