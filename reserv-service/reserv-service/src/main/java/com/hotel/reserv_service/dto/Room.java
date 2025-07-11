package com.hotel.reserv_service.dto;



//package com.hotel.room_service.dto;

//import com.hotel.room_service.model.RoomStatus;
import com.hotel.reserv_service.dto.RoomStatus;


public class Room {
    private Long id;
    private String roomNumber;
    private String roomType;
    private Double rate;
    private RoomStatus status; 

    public Room() {}

    public Room(Long id, String roomNumber, String roomType, Double rate, RoomStatus status) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.rate = rate;
        this.status = status;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public RoomStatus getStatus() { return status; }
    public void setStatus(RoomStatus status) { this.status = status; }
}

