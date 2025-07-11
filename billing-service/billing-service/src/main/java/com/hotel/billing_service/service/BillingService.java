package com.hotel.billing_service.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hotel.billing_service.client.ReservationClient;
import com.hotel.billing_service.dto.Reservation;
import com.hotel.billing_service.entity.Bill;
import com.hotel.billing_service.repository.BillRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BillingService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ReservationClient reservationClient;

    public Bill generateBill(Long reservationId, double taxes) {
        Reservation reservation = reservationClient.getReservationById(reservationId);
        double totalAmount = reservation.getTotalAmount();

        double totalCost = totalAmount + taxes;

        Bill bill = new Bill();
        bill.setReservationId(reservationId);
        bill.setTotalAmount(totalAmount);
        bill.setTaxes(taxes);
        bill.setTotalCost(totalCost);
        bill.setBillingDate(LocalDate.now());

        return billRepository.save(bill);
    }
    public Bill getBillByReservationId(Long reservationId) {
        return billRepository.findByReservationId(reservationId);
    }

}



