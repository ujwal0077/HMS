package com.hotel.shared.events;


import java.io.Serializable;


public class ReservationEvent implements Serializable{
    private Long reservationId;
    private Long guestId;
    private Long roomId;

    public ReservationEvent() {}

    public ReservationEvent(Long reservationId, Long guestId, Long roomId) {
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.roomId = roomId;
    }

    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public Long getGuestId() { return guestId; }
    public void setGuestId(Long guestId) { this.guestId = guestId; }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
}


