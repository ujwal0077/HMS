//package com.hotel.guest_service.listner;
//
//
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//
//import org.springframework.stereotype.Component;
//
//import com.hotel.shared.events.ReservationEvent;
//
////import com.hotel.guest_service.events.ReservationEvent;
//
//@Component
//public class ReservationListner {
//
//    @RabbitListener(queues = "reservationQueue")
//    public void handleReservation(ReservationEvent event) {
//    	
//    	
//        System.out.println("Received reservation event:");
//        System.out.println("Reservation ID: " + event.getReservationId());
//        System.out.println("Guest ID: " + event.getGuestId());
//        System.out.println("Room ID: " + event.getRoomId());
//
//        // TODO: Save or process guest info here if needed
//    }
//}
//
package com.hotel.guest_service.listner;

//import com.hotel.guest_service.events.ReservationEvent;
import com.hotel.guest_service.model.Guest;
import com.hotel.guest_service.repo.GuestRepository;
import com.hotel.guest_service.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hotel.shared.events.ReservationEvent;

@Component
public class ReservationListner {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "reservationQueue")
    public void handleReservation(ReservationEvent event) {
        System.out.println("Received reservation event:");
        System.out.println("Reservation ID: " + event.getReservationId());
        System.out.println("Guest ID: " + event.getGuestId());
        System.out.println("Room ID: " + event.getRoomId());

        Guest guest = guestRepository.findById(event.getGuestId()).orElse(null);
        if (guest != null) {
            emailService.sendReservationEmail(
                guest.getEmail(),
                guest.getName(),
                event.getReservationId(),
                event.getRoomId()
            );
        }
    }
}
