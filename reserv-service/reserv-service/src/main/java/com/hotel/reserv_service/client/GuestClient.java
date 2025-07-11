package com.hotel.reserv_service.client;




import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.hotel.reserv_service.dto.Guest;

@FeignClient(name = "guest-service")
public interface GuestClient {

    @GetMapping("/api/guests/{id}")
    Guest getGuestById(@PathVariable("id") Long id);
}

