package com.hotel.billing_service.dto;



import java.time.LocalDate;

public class Reservation {
    private Long id;
    private Long guestId;
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int nights;
    private double totalAmount;

    // Getters
    public Long getId() {
        return id;
    }

    public Long getGuestId() {
        return guestId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // toString()
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", guestId=" + guestId +
                ", roomId=" + roomId +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", nights=" + nights +
                ", totalAmount=" + totalAmount +
                '}';
    }
}

