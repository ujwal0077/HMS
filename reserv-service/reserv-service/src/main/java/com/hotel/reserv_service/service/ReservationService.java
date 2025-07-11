//package com.hotel.reserv_service.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.hotel.reserv_service.client.GuestClient;
//import com.hotel.reserv_service.client.RoomClient;
//import com.hotel.reserv_service.dto.Guest;
//import com.hotel.reserv_service.dto.Room;
//import com.hotel.reserv_service.dto.RoomStatus;
//import com.hotel.reserv_service.entity.Reservation;
//import com.hotel.reserv_service.repo.ReservationRepository;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//@Service
//public class ReservationService {
//
//    @Autowired
//    private ReservationRepository repository;
//
//    @Autowired
//    private GuestClient guestClient;
//
//    @Autowired
//    private RoomClient roomClient;
//
//    public Reservation bookReservation(Reservation reservation) {
//        // Fetch room and guest data
//        Room room = roomClient.getRoomById(reservation.getRoomId());
//        Guest guest = guestClient.getGuestById(reservation.getGuestId());
//
//        if (room == null || guest == null) {
//            throw new RuntimeException("Invalid Guest or Room ID.");
//        }
//
//        // Check if room is under maintenance
//        if ("MAINTENANCE".equals(room.getStatus())) {
//            throw new RuntimeException("Room is currently under maintenance and cannot be booked.");
//        }
//        
//
//
//        
//        
//        // Calculate the number of nights
//        LocalDate checkIn = reservation.getCheckIn();
//        LocalDate checkOut = reservation.getCheckOut();
//        if (checkIn == null || checkOut == null) {
//            throw new IllegalArgumentException("Check-in and Check-out dates must not be null.");
//        }
//        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
//        
//        if (nights <= 0) {
//            throw new IllegalArgumentException("Stay duration must be at least one night.");
//        }
//
//        reservation.setNights((int) nights);
//        if (room.getRate() == null) {
//            throw new RuntimeException("Room rate is not set for room with ID " + room.getId());
//        }
//        // 💰 Calculate total amount
//        double totalAmount = room.getRate() * nights;
//        reservation.setTotalAmount(totalAmount);
//
//        // Check for overlapping reservations
//        List<Reservation> overlappingReservations = repository
//                .findByRoomIdAndReservationDateBetween(reservation.getRoomId(), checkIn, checkOut.minusDays(1));
//
//        if (!overlappingReservations.isEmpty()) {
//            throw new RuntimeException("Room is already booked for the selected date range.");
//        }
//
//        // Set reservation fields
//        reservation.setStatus("CONFIRMED");
//        reservation.setCreatedAt(LocalDateTime.now());
//        reservation.setReservationDate(checkIn);
//
//        // Save reservation for each night
//
//
//        Reservation savedReservation = repository.save(reservation);
//
//
//        // After successful booking, update room status to "BOOKED"
//        room.setStatus(RoomStatus.BOOKED);
//        roomClient.updateRoom(room.getId(), room);
//
//        return savedReservation;
//    }
//
//    public boolean checkAvailability(Long roomId, LocalDate date) {
//        Room room = roomClient.getRoomById(roomId);
//        if (room == null) {
//            throw new RuntimeException("Room ID " + roomId + " is invalid or does not exist.");
//        }
//
//        return repository.findByRoomIdAndReservationDate(roomId, date).isEmpty();
//    }
//    
//    public Reservation getReservationById(Long id) {
//        return repository.findById(id)
//               .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
//    }
//    
//    @Autowired
//    private ReservationPublisher publisher;
//
//    public Reservation bookReservation(Reservation reservation) {
//        reservation.setCreatedAt(LocalDateTime.now());
//        Reservation saved = reservationRepository.save(reservation);
//        
//        ReservationEvent event = new ReservationEvent(
//            saved.getId(), saved.getGuestId(), saved.getRoomId()
//        );
//        publisher.sendReservationEvent(event);
//
//        return saved;
//    }
//
//
//}
package com.hotel.reserv_service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.reserv_service.client.GuestClient;
import com.hotel.reserv_service.client.RoomClient;
import com.hotel.reserv_service.dto.Guest;
import com.hotel.reserv_service.dto.Room;
import com.hotel.reserv_service.entity.Reservation;
import com.hotel.reserv_service.repo.ReservationRepository;
import com.hotel.shared.events.ReservationEvent;
import com.hotel.reserv_service.publisher.ReservationPublisher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private GuestClient guestClient;

    @Autowired
    private RoomClient roomClient;

    @Autowired
    private ReservationPublisher publisher;

    private static final String RESERVATION_SERVICE = "reservationService";

    @CircuitBreaker(name = RESERVATION_SERVICE, fallbackMethod = "bookReservationFallback")
    public Reservation bookReservation(Reservation reservation) {
        // (Your existing booking logic here, unchanged)
        Room room = roomClient.getRoomById(reservation.getRoomId());
        Guest guest = guestClient.getGuestById(reservation.getGuestId());

        if (room == null || guest == null) {
            throw new RuntimeException("Invalid Guest or Room ID.");
        }

        if ("MAINTENANCE".equals(room.getStatus())) {
            throw new RuntimeException("Room is currently under maintenance and cannot be booked.");
        }

        LocalDate checkIn = reservation.getCheckIn();
        LocalDate checkOut = reservation.getCheckOut();

        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and Check-out dates must not be null.");
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);

        if (nights <= 0) {
            throw new IllegalArgumentException("Stay duration must be at least one night.");
        }

        reservation.setNights((int) nights);

        if (room.getRate() == null) {
            throw new RuntimeException("Room rate is not set for room with ID " + room.getId());
        }

        double totalAmount = room.getRate() * nights;
        reservation.setTotalAmount(totalAmount);

        List<Reservation> overlappingReservations = repository
                .findByRoomIdAndReservationDateBetween(reservation.getRoomId(), checkIn, checkOut.minusDays(1));

        if (!overlappingReservations.isEmpty()) {
            throw new RuntimeException("Room is already booked for the selected date range.");
        }

        reservation.setStatus("CONFIRMED");
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setReservationDate(checkIn);

        Reservation savedReservation = repository.save(reservation);

        roomClient.updateRoom(room.getId(), room);

        ReservationEvent event = new ReservationEvent(
                savedReservation.getId(), savedReservation.getGuestId(), savedReservation.getRoomId()
        );
        publisher.sendReservationEvent(event);

        return savedReservation;
    }

    // Fallback method for Circuit Breaker
    public Reservation bookReservationFallback(Reservation reservation, Throwable throwable) {
        // Log the error or notify
        System.err.println("Fallback invoked for bookReservation due to: " + throwable.getMessage());

        // Return a meaningful fallback response or throw custom exception
        throw new RuntimeException("Reservation service is currently unavailable. Please try again later.");
    }

    @CircuitBreaker(name = RESERVATION_SERVICE, fallbackMethod = "checkAvailabilityFallback")
    public boolean checkAvailability(Long roomId, LocalDate date) {
        Room room = roomClient.getRoomById(roomId);
        if (room == null) {
            throw new RuntimeException("Room ID " + roomId + " is invalid or does not exist.");
        }

        return repository.findByRoomIdAndReservationDate(roomId, date).isEmpty();
    }

    // Fallback for checkAvailability
    public boolean checkAvailabilityFallback(Long roomId, LocalDate date, Throwable throwable) {
        System.err.println("Fallback invoked for checkAvailability due to: " + throwable.getMessage());
        // Return false or some default value
        return false;
    }

    public Reservation getReservationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }
    public List<Room> getAvailableRoomsInRange(LocalDate checkIn, LocalDate checkOut) {
        List<Room> allRooms = roomClient.getAllRooms();

        // Filter out rooms under maintenance or null
        List<Room> filteredRooms = allRooms.stream()
                .filter(room -> room != null && !"MAINTENANCE".equalsIgnoreCase(room.getStatus().name()))
                .collect(Collectors.toList());

        return filteredRooms.stream()
                .filter(room -> {
                    List<Reservation> reservations = repository.findByRoomIdAndReservationDateBetween(
                            room.getId(), checkIn, checkOut.minusDays(1));
                    return reservations.isEmpty(); // Available if no overlapping reservations
                })
                .collect(Collectors.toList());
    }

}
