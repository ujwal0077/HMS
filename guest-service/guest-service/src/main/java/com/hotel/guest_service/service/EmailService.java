package com.hotel.guest_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendReservationEmail(String to, String name, Long reservationId, Long roomId) {
        String subject = "Reservation Confirmed!";
        String text = String.format(
            "Hi %s,\n\nYour reservation (ID: %d) for room %d is confirmed.\n\nThanks for booking with us!",
            name, reservationId, roomId
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("freakyfi0077@gmail.com"); // your sender email
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}

