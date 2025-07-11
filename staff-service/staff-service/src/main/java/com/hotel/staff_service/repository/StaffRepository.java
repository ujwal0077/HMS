package com.hotel.staff_service.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.staff_service.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByCode(String code);
}

