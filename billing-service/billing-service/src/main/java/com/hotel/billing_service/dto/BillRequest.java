package com.hotel.billing_service.dto;

public class BillRequest {
    private Long reservationId;
    private double taxes;
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public double getTaxes() {
		return taxes;
	}
	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}
	public BillRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "BillRequest [reservationId=" + reservationId + ", taxes=" + taxes + "]";
	}

    // Getters and Setters
    
}
