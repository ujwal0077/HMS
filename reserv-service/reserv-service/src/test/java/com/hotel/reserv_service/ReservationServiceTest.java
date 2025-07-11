package com.hotel.reserv_service;

//package com.hotel.reserv_service;


import com.hotel.reserv_service.client.GuestClient;
import com.hotel.reserv_service.client.RoomClient;
import com.hotel.reserv_service.dto.*;
import com.hotel.reserv_service.entity.Reservation;
import com.hotel.reserv_service.publisher.ReservationPublisher;
import com.hotel.reserv_service.repo.ReservationRepository;
import com.hotel.reserv_service.service.ReservationService;
import com.hotel.shared.events.ReservationEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository repository;

    @Mock
    private GuestClient guestClient;

    @Mock
    private RoomClient roomClient;

    @Mock
    private ReservationPublisher publisher;

    private Room testRoom;
    private Guest testGuest;
    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testRoom = new Room();
        testRoom.setId(1L);
        testRoom.setRate(1000.0);
        testRoom.setStatus(RoomStatus.AVAILABLE);

        testGuest = new Guest();
        testGuest.setId(1L);

        reservation = new Reservation();
        reservation.setGuestId(1L);
        reservation.setRoomId(1L);
        reservation.setCheckIn(LocalDate.now().plusDays(1));
        reservation.setCheckOut(LocalDate.now().plusDays(3));
    }

    @Test
    public void testBookReservation_Success() {
        when(roomClient.getRoomById(1L)).thenReturn(testRoom);
        when(guestClient.getGuestById(1L)).thenReturn(testGuest);
        when(repository.findByRoomIdAndReservationDateBetween(eq(1L), any(), any()))
                .thenReturn(Collections.emptyList());
        when(repository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation saved = invocation.getArgument(0);
            saved.setId(100L);
            return saved;
        });

        Reservation saved = reservationService.bookReservation(reservation);

        assertEquals("CONFIRMED", saved.getStatus());
        assertEquals(2, saved.getNights());
        assertEquals(2000.0, saved.getTotalAmount());

        verify(publisher, times(1)).sendReservationEvent(any(ReservationEvent.class));
    }

  

    @Test
    public void testBookReservation_OverlappingReservation() {
        when(roomClient.getRoomById(1L)).thenReturn(testRoom);
        when(guestClient.getGuestById(1L)).thenReturn(testGuest);
        when(repository.findByRoomIdAndReservationDateBetween(eq(1L), any(), any()))
                .thenReturn(List.of(new Reservation()));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservationService.bookReservation(reservation));

        assertEquals("Room is already booked for the selected date range.", ex.getMessage());
    }

    @Test
    public void testBookReservation_InvalidDates() {
        reservation.setCheckOut(reservation.getCheckIn());

        when(roomClient.getRoomById(1L)).thenReturn(testRoom);
        when(guestClient.getGuestById(1L)).thenReturn(testGuest);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> reservationService.bookReservation(reservation));

        assertEquals("Stay duration must be at least one night.", ex.getMessage());
    }

    @Test
    public void testBookReservation_AlreadyBooked() {
        // Create a mock reservation list
        List<Reservation> reservations = List.of(new Reservation());

        when(roomClient.getRoomById(1L)).thenReturn(testRoom);
        when(guestClient.getGuestById(1L)).thenReturn(testGuest);
        when(repository.findByRoomIdAndReservationDateBetween(eq(1L), any(), any()))
            .thenReturn(reservations);

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> reservationService.bookReservation(reservation));

        assertEquals("Room is already booked for the selected date range.", ex.getMessage());
    }


    @Test
    public void testGetReservationById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservationService.getReservationById(1L));

        assertEquals("Reservation not found with id: 1", ex.getMessage());
    }
}

