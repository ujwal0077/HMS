package com.hotel.staff_service;
//
//
//
//
//import com.hotel.staff_service.controller.StaffController;
//import com.hotel.staff_service.entity.Staff;
//import com.hotel.staff_service.service.StaffService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//class StaffControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private StaffService staffService;
//
//    @InjectMocks
//    private StaffController staffController;
//
//    private Staff staff;
//
//    @BeforeEach
//    void setUp() {
//        staff = new Staff(1L, "John Doe", "STF123", "HR", "9876543210");
//        mockMvc = MockMvcBuilders.standaloneSetup(staffController).build();
//    }
//
//    @Test
//    void getAllStaff() throws Exception {
//        // Given
//        when(staffService.getAllStaff()).thenReturn(List.of(staff));
//
//        // When & Then
//        mockMvc.perform(get("/api/staff/all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("John Doe"));
//        
//        verify(staffService, times(1)).getAllStaff();
//    }
//
//    @Test
//    void getStaffById() throws Exception {
//        // Given
//        when(staffService.getStaffById(1L)).thenReturn(staff);
//
//        // When & Then
//        mockMvc.perform(get("/api/staff/{id}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("John Doe"));
//
//        verify(staffService, times(1)).getStaffById(1L);
//    }
//
//    @Test
//    void getStaffByCode() throws Exception {
//        // Given
//        when(staffService.getStaffByCode("STF123")).thenReturn(staff);
//
//        // When & Then
//        mockMvc.perform(get("/api/staff/code/{code}", "STF123"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("John Doe"));
//
//        verify(staffService, times(1)).getStaffByCode("STF123");
//    }
//
//    @Test
//    void addStaff() throws Exception {
//        // Given
//        when(staffService.addStaff(any(Staff.class))).thenReturn("Staff added successfully");
//
//        // When & Then
//        mockMvc.perform(post("/api/staff")
//                        .contentType("application/json")
//                        .content("{ \"name\": \"John Doe\", \"code\": \"STF123\", \"department\": \"HR\", \"contact\": \"9876543210\" }"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Staff added successfully"));
//
//        verify(staffService, times(1)).addStaff(any(Staff.class));
//    }
//
//    @Test
//    void updateStaff() throws Exception {
//        // Given
//        when(staffService.updateStaff(eq(1L), any(Staff.class))).thenReturn("Staff updated successfully");
//
//        // When & Then
//        mockMvc.perform(put("/api/staff/{id}", 1L)
//                        .contentType("application/json")
//                        .content("{ \"name\": \"John Updated\", \"code\": \"STF123\", \"department\": \"HR\", \"contact\": \"9876543210\" }"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Staff updated successfully"));
//
//        verify(staffService, times(1)).updateStaff(eq(1L), any(Staff.class));
//    }
//
//    @Test
//    void deleteStaff() throws Exception {
//        // Given
//        when(staffService.deleteStaff(1L)).thenReturn("Staff deleted successfully");
//
//        // When & Then
//        mockMvc.perform(delete("/api/staff/{id}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Staff deleted successfully"));
//
//        verify(staffService, times(1)).deleteStaff(1L);
//    }
//}
//
//package com.hotel.staff_service.service;

import com.hotel.staff_service.entity.Staff;
import com.hotel.staff_service.repository.StaffRepository;
import com.hotel.staff_service.service.StaffService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffService staffService;

    private Staff sampleStaff;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleStaff = new Staff(1L, "John Doe", "EMP123", "9876543210", "Housekeeping");
    }

    @Test
    void testGetAllStaff() {
        List<Staff> mockList = List.of(sampleStaff);
        when(staffRepository.findAll()).thenReturn(mockList);

        List<Staff> result = staffService.getAllStaff();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void testGetStaffById_Found() {
        when(staffRepository.findById(1L)).thenReturn(Optional.of(sampleStaff));

        Staff result = staffService.getStaffById(1L);

        assertNotNull(result);
        assertEquals("EMP123", result.getCode());
    }

    @Test
    void testGetStaffById_NotFound() {
        when(staffRepository.findById(1L)).thenReturn(Optional.empty());

        Staff result = staffService.getStaffById(1L);

        assertNull(result);
    }


    @Test
    void testAddStaff() {
        when(staffRepository.save(any())).thenReturn(sampleStaff);

        String result = staffService.addStaff(sampleStaff);

        assertEquals("Staff added successfully", result);
    }

    @Test
    void testUpdateStaff_Found() {
        Staff updated = new Staff(null, "Jane Smith", "EMP123", "9999999999", "Front Desk");

        when(staffRepository.findById(1L)).thenReturn(Optional.of(sampleStaff));
        when(staffRepository.save(any())).thenReturn(sampleStaff);

        String result = staffService.updateStaff(1L, updated);

        assertEquals("Staff updated successfully", result);
        verify(staffRepository).save(any(Staff.class));
    }

    @Test
    void testUpdateStaff_NotFound() {
        when(staffRepository.findById(1L)).thenReturn(Optional.empty());

        String result = staffService.updateStaff(1L, sampleStaff);

        assertEquals("Staff not found", result);
    }

    @Test
    void testDeleteStaff_Found() {
        when(staffRepository.existsById(1L)).thenReturn(true);

        String result = staffService.deleteStaff(1L);

        assertEquals("Staff deleted successfully", result);
        verify(staffRepository).deleteById(1L);
    }

    @Test
    void testDeleteStaff_NotFound() {
        when(staffRepository.existsById(1L)).thenReturn(false);

        String result = staffService.deleteStaff(1L);

        assertEquals("Staff not found", result);
    }
}
