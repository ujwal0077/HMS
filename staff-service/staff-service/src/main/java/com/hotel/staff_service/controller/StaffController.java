package com.hotel.staff_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hotel.staff_service.entity.Staff;
import com.hotel.staff_service.service.StaffService;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@Tag(name = "Staff Management", description = "CRUD operations for Staff entity")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @Operation(summary = "Get all staff members")
    @GetMapping("/all")
    public ResponseEntity<List<Staff>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @Operation(summary = "Get staff member by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        Staff staff = staffService.getStaffById(id);
        return staff != null ? ResponseEntity.ok(staff) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get staff member by code")
    @GetMapping("/code/{code}")
    public ResponseEntity<Staff> getStaffByCode(@PathVariable String code) {
        Staff staff = staffService.getStaffByCode(code);
        return staff != null ? ResponseEntity.ok(staff) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Add new staff member")
    @PostMapping
    public ResponseEntity<String> addStaff(@RequestBody Staff staff) {
        return ResponseEntity.ok(staffService.addStaff(staff));
    }

    @Operation(summary = "Update staff member by ID")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStaff(@PathVariable Long id, @RequestBody Staff staff) {
        String result = staffService.updateStaff(id, staff);
        return result.contains("successfully") ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @Operation(summary = "Delete staff member by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        String result = staffService.deleteStaff(id);
        return result.contains("successfully") ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}
