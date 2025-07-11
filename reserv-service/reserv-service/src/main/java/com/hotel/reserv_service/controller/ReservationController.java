package com.hotel.reserv_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hotel.reserv_service.client.RoomClient;
import com.hotel.reserv_service.dto.Room;
import com.hotel.reserv_service.entity.Reservation;
import com.hotel.reserv_service.service.ReservationService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@RestController
//@RequestMapping("/api/reservations")
//public class ReservationController {
//
//    @Autowired
//    private ReservationService service;
//
//    // POST: Book a reservation
//    @PostMapping("/book")
//    public ResponseEntity<Reservation> bookReservation(@RequestBody Reservation reservation) {
//        Reservation saved = service.bookReservation(reservation);
//        return ResponseEntity.ok(saved);
//    }
//
//    @GetMapping("/availability")
//    public ResponseEntity<Map<String, Object>> checkAvailability(
//            @RequestParam Long roomId,
//            @RequestParam String date
//    ) {
//        try {
//            boolean available = service.checkAvailability(roomId, LocalDate.parse(date));
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("roomId", roomId);
//            response.put("date", date);
//            response.put("available", available);
//
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            // Return a 400 Bad Request response with the error message if roomId is invalid
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("error", e.getMessage());
//
//            // Return the error response with 400 status
//            return ResponseEntity.badRequest().body(errorResponse);
//        }
//    }
//
//}

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservation Controller", description = "Operations related to reservation booking and availability")
public class ReservationController {

    @Autowired
    private ReservationService service;

    @Autowired
    private RoomClient roomClient;

    @PostMapping("/book")
    @Operation(summary = "Book a reservation", description = "Creates a new reservation for a room and guest")
    public ResponseEntity<Reservation> bookReservation(@RequestBody Reservation reservation) {
        Reservation saved = service.bookReservation(reservation);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/availability")
    @Operation(summary = "Check room availability", description = "Checks if a specific room is available on a specific date")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @RequestParam Long roomId,
            @RequestParam String date
    ) {
        try {
            boolean available = service.checkAvailability(roomId, LocalDate.parse(date));

            Map<String, Object> response = new HashMap<>();
            response.put("roomId", roomId);
            response.put("date", date);
            response.put("available", available);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get reservation by ID")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = service.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }


    @GetMapping("/rooms/available")
    @Operation(summary = "Get all available rooms")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        List<Room> allRooms = roomClient.getAllRooms();
        List<Room> availableRooms = allRooms.stream()
        		.filter(room -> room.getStatus().name().equalsIgnoreCase("AVAILABLE"))
                .collect(Collectors.toList());
        return ResponseEntity.ok(availableRooms);
    }

    @GetMapping("/rooms/{id}")
    @Operation(summary = "Get room details by ID")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomClient.getRoomById(id);
        return ResponseEntity.ok(room);
    }
    @GetMapping("/rooms/available-range")
    @Operation(summary = "Get available rooms in date range", description = "Fetch all available rooms between check-in and check-out dates")
    public ResponseEntity<List<Room>> getAvailableRoomsInDateRange(
            @RequestParam String checkIn,
            @RequestParam String checkOut) {

        LocalDate inDate = LocalDate.parse(checkIn);
        LocalDate outDate = LocalDate.parse(checkOut);

        List<Room> availableRooms = service.getAvailableRoomsInRange(inDate, outDate);
        return ResponseEntity.ok(availableRooms);
    }

}


