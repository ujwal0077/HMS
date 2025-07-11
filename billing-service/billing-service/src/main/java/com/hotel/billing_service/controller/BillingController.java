package com.hotel.billing_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hotel.billing_service.dto.BillRequest;
import com.hotel.billing_service.entity.Bill;
import com.hotel.billing_service.service.BillingService;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping("/generate")
    public ResponseEntity<Bill> generateBill(@RequestBody BillRequest request) {
        Bill bill = billingService.generateBill(request.getReservationId(), request.getTaxes());
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }
    @GetMapping("/getByReservationId/{id}")
    public ResponseEntity<Bill> getBillByReservationId(@PathVariable Long id) {
        Bill bill = billingService.getBillByReservationId(id);
        if (bill == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

}
