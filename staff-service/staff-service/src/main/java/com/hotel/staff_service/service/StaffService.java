package com.hotel.staff_service.service;

import org.springframework.stereotype.Service;

import com.hotel.staff_service.entity.Staff;
import com.hotel.staff_service.repository.StaffRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    public Staff getStaffByCode(String code) {
        return staffRepository.findByCode(code);
    }

    public String addStaff(Staff staff) {
        staffRepository.save(staff);
        return "Staff added successfully";
    }

    public String updateStaff(Long id, Staff updatedStaff) {
        Optional<Staff> optional = staffRepository.findById(id);
        if (optional.isPresent()) {
            Staff existing = optional.get();
            existing.setName(updatedStaff.getName());
            existing.setCode(updatedStaff.getCode());
            existing.setContact(updatedStaff.getContact());
            existing.setDepartment(updatedStaff.getDepartment());
            staffRepository.save(existing);
            return "Staff updated successfully";
        }
        return "Staff not found";
    }

    public String deleteStaff(Long id) {
        if (staffRepository.existsById(id)) {
            staffRepository.deleteById(id);
            return "Staff deleted successfully";
        }
        return "Staff not found";
    }
}

