package com.hotel.billing_service.controller;

import com.hotel.billing_service.entity.Bill;
import com.hotel.billing_service.repository.BillRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final BillRepository billRepository;

    public PaymentController(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    @PostMapping("/create-order/{reservationId}")
    public String createPaymentOrder(@PathVariable Long reservationId) {
        try {
            Bill bill = billRepository.findByReservationId(reservationId);
            if (bill == null) {
                return "Bill not found for Reservation ID: " + reservationId;
            }
            System.out.println("before razor");
            RazorpayClient razorpay = new RazorpayClient(razorpayKey, razorpaySecret);
            System.out.println("after razor");
            int amountInPaise = (int) (bill.getTotalCost() * 100); // Razorpay expects paise

            JSONObject options = new JSONObject();
            options.put("amount", amountInPaise);
            options.put("currency", "INR");
            options.put("receipt", "bill_rcptid_" + reservationId);
            options.put("payment_capture", 1);
            System.out.println("before razor");
            Order order = razorpay.orders.create(options);
            System.out.println("before razor");
            return order.toString(); // You can parse & send only required fields if needed

        } catch (Exception e) {
            e.printStackTrace();
            return "Error while creating order: " + e.getMessage();
        }
    }
}