package com.hotel.room_service;

//package com.hotel.room_service;

import com.hotel.room_service.model.Room;

import com.hotel.room_service.model.RoomStatus;
import com.hotel.room_service.repo.RoomRepository;
import com.hotel.room_service.service.RoomService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    private Room sampleRoom;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleRoom = new Room("101", "Deluxe", 2000.0, RoomStatus.AVAILABLE);
        sampleRoom.setId(1L);
    }

    @Test
    void getAllRooms_shouldReturnRooms() {
        when(roomRepository.findAll()).thenReturn(List.of(sampleRoom));
        List<Room> rooms = roomService.getAllRooms();
        assertEquals(1, rooms.size());
    }

    @Test
    void createRoom_shouldSaveRoom() {
        when(roomRepository.save(any(Room.class))).thenReturn(sampleRoom);
        Room saved = roomService.createRoom(sampleRoom);
        assertNotNull(saved);
        assertEquals("101", saved.getRoomNumber());
    }

    @Test
    void updateRoom_shouldUpdateIfExists() {
        Room updated = new Room("102", "Suite", 3000.0, RoomStatus.BOOKED);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(sampleRoom));
        when(roomRepository.save(any())).thenReturn(updated);

        Room result = roomService.updateRoom(1L, updated);
        assertNotNull(result);
        assertEquals("102", result.getRoomNumber());
    }

    @Test
    void updateRoom_shouldReturnNullIfNotFound() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());
        Room result = roomService.updateRoom(1L, sampleRoom);
        assertNull(result);
    }

    @Test
    void deleteRoom_shouldReturnTrueIfExists() {
        when(roomRepository.existsById(1L)).thenReturn(true);
        boolean result = roomService.deleteRoom(1L);
        assertTrue(result);
    }

    @Test
    void deleteRoom_shouldReturnFalseIfNotExists() {
        when(roomRepository.existsById(1L)).thenReturn(false);
        boolean result = roomService.deleteRoom(1L);
        assertFalse(result);
    }

    @Test
    void updateRate_shouldChangeRateIfExists() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(sampleRoom));
        sampleRoom.setRate(2500.0);
        when(roomRepository.save(any())).thenReturn(sampleRoom);

        Room result = roomService.updateRate(1L, 2500.0);
        assertEquals(2500.0, result.getRate());
    }
}
