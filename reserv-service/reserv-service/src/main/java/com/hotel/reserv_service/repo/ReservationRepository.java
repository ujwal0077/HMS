package com.hotel.reserv_service.repo;




import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.reserv_service.entity.Reservation;

import java.time.LocalDate;
import java.util.Optional;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByRoomIdAndReservationDate(Long roomId, LocalDate reservationDate);

    List<Reservation> findByRoomIdAndReservationDateBetween(Long roomId, LocalDate startDate, LocalDate endDate);
}

