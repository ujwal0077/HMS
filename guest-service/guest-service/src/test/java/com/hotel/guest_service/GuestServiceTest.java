package com.hotel.guest_service;



import com.hotel.guest_service.model.Guest;
import com.hotel.guest_service.repo.GuestRepository;
import com.hotel.guest_service.service.GuestService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GuestServiceTest {

    @InjectMocks
    private GuestService guestService;

    @Mock
    private GuestRepository guestRepository;

    private Guest guest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        guest = new Guest(1L, "Ujwal", "ujwal@gmail.com", "9876543210");
    }

    @Test
    void testAddGuest() {
        when(guestRepository.save(guest)).thenReturn(guest);
        Guest saved = guestService.addGuest(guest);
        assertEquals("Ujwal", saved.getName());
    }

    @Test
    void testGetAllGuests() {
        List<Guest> guests = Arrays.asList(guest, new Guest(2L, "Prajwal", "praj@example.com", "9876500000"));
        when(guestRepository.findAll()).thenReturn(guests);
        List<Guest> result = guestService.getAllGuests();
        assertEquals(2, result.size());
    }

    @Test
    void testGetGuestById_Found() {
        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
        Optional<Guest> result = guestService.getGuestById(1L);
        assertTrue(result.isPresent());
        assertEquals("Ujwal", result.get().getName());
    }

    @Test
    void testGetGuestById_NotFound() {
        when(guestRepository.findById(3L)).thenReturn(Optional.empty());
        Optional<Guest> result = guestService.getGuestById(3L);
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateGuest_Found() {
        Guest updated = new Guest(1L, "Ujwal S", "ujwal.s@example.com", "9999999999");
        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
        when(guestRepository.save(any(Guest.class))).thenReturn(updated);

        Guest result = guestService.updateGuest(1L, updated);
        assertEquals("Ujwal S", result.getName());
        assertEquals("9999999999", result.getPhone());
    }

    @Test
    void testUpdateGuest_NotFound() {
        when(guestRepository.findById(3L)).thenReturn(Optional.empty());
        Guest result = guestService.updateGuest(3L, guest);
        assertNull(result);
    }

    @Test
    void testDeleteGuest_Exists() {
        when(guestRepository.existsById(1L)).thenReturn(true);
        boolean result = guestService.deleteGuest(1L);
        assertTrue(result);
        verify(guestRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteGuest_NotExists() {
        when(guestRepository.existsById(3L)).thenReturn(false);
        boolean result = guestService.deleteGuest(3L);
        assertFalse(result);
    }
}
