package com.hotel.guest_service.repo;

//package com.hotel.guest_service.repo;

import com.hotel.guest_service.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}

