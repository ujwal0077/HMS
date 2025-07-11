package com.hotel.room_service.model;

//package com.roomservice.model;

//package com.roomservice.model;

import jakarta.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 350) 
    private String roomNumber;
    
    @Column(length = 350) 
    private String roomType;
    
    @Column(length = 350) 
    private Double rate;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;  // NEW ENUM FIELD

    // Constructors
    public Room() {}

    public Room(String roomNumber, String roomType, Double rate, RoomStatus status) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.rate = rate;
        this.status = status;
    }

    // Getters and Setters
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
