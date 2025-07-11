package com.hotel.billing_service;

//package com.hotel.billing_service.service;

import com.hotel.billing_service.client.ReservationClient;
import com.hotel.billing_service.dto.Reservation;
import com.hotel.billing_service.entity.Bill;
import com.hotel.billing_service.repository.BillRepository;
import com.hotel.billing_service.service.BillingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillingServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private ReservationClient reservationClient;

    @InjectMocks
    private BillingService billingService;

    private Reservation mockReservation;
    private Bill savedBill;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockReservation = new Reservation();
        mockReservation.setId(1L);
        mockReservation.setTotalAmount(5000.0);

        savedBill = new Bill();
        savedBill.setBillId(1L);
        savedBill.setReservationId(1L);
        savedBill.setTotalAmount(5000.0);
        savedBill.setTaxes(500.0);
        savedBill.setTotalCost(5500.0);
        savedBill.setBillingDate(LocalDate.now());
    }

    @Test
    void testGenerateBill() {
        when(reservationClient.getReservationById(1L)).thenReturn(mockReservation);
        when(billRepository.save(any(Bill.class))).thenReturn(savedBill);

        Bill bill = billingService.generateBill(1L, 500.0);

        assertNotNull(bill);
        assertEquals(1L, bill.getReservationId());
        assertEquals(5000.0, bill.getTotalAmount());
        assertEquals(500.0, bill.getTaxes());
        assertEquals(5500.0, bill.getTotalCost());
    }

    @Test
    void testGetBillByReservationId() {
        when(billRepository.findByReservationId(1L)).thenReturn(savedBill);

        Bill result = billingService.getBillByReservationId(1L);

        assertNotNull(result);
        assertEquals(5500.0, result.getTotalCost());
    }
}

