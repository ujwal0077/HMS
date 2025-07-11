package com.hotel.guest_service.controller;

//package com.hotel.guest_service.controller;

import com.hotel.guest_service.model.Guest;
import com.hotel.guest_service.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@RestController
//@RequestMapping("/api/guests")
//public class GuestController {
//
//    @Autowired
//    private GuestService guestService;
//
//    @PostMapping
//    public Guest addGuest(@RequestBody Guest guest) {
//        return guestService.addGuest(guest);
//    }
//
//    @GetMapping
//    public List<Guest> getAllGuests() {
//        return guestService.getAllGuests();
//    }
//
//    @GetMapping("/{id}")
//    public Optional<Guest> getGuestById(@PathVariable Long id) {
//        return guestService.getGuestById(id);
//    }
//
//    @PutMapping("/{id}")
//    public Guest updateGuest(@PathVariable Long id, @RequestBody Guest guest) {
//        return guestService.updateGuest(id, guest);
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteGuest(@PathVariable Long id) {
//        boolean isDeleted = guestService.deleteGuest(id);
//        return isDeleted ? "Guest deleted successfully" : "Guest not found";
//    }
//}

//package com.hotel.guest_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guest Management", description = "CRUD APIs for managing hotel guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @Operation(summary = "Add a new guest")
    @PostMapping
    public Guest addGuest(@RequestBody Guest guest) {
        return guestService.addGuest(guest);
    }

    @Operation(summary = "Get all guests")
    @GetMapping
    public List<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }

    @Operation(summary = "Get guest by ID")
    @GetMapping("/{id}")
    public Optional<Guest> getGuestById(@PathVariable Long id) {
        return guestService.getGuestById(id);
    }

    @Operation(summary = "Update guest details")
    @PutMapping("/{id}")
    public Guest updateGuest(@PathVariable Long id, @RequestBody Guest guest) {
        return guestService.updateGuest(id, guest);
    }

    @Operation(summary = "Delete guest by ID")
    @DeleteMapping("/{id}")
    public String deleteGuest(@PathVariable Long id) {
        boolean deleted = guestService.deleteGuest(id);
        return deleted ? "Guest deleted successfully" : "Guest not found";
    }
}

