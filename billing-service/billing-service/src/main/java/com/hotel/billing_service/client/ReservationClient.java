package com.hotel.billing_service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hotel.billing_service.dto.Reservation;

@FeignClient(name = "RESERVATION-SERVICE")
public interface ReservationClient {
    @GetMapping("api/reservations/{id}")
    Reservation getReservationById(@PathVariable("id") Long id);
}

