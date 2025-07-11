package com.hotel.guest_service.service;

//package com.hotel.guest_service.service;

import com.hotel.guest_service.model.Guest;
import com.hotel.guest_service.repo.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    public Guest addGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Optional<Guest> getGuestById(Long id) {
        return guestRepository.findById(id);
    }

    public Guest updateGuest(Long id, Guest updatedGuest) {
        return guestRepository.findById(id).map(guest -> {
            guest.setName(updatedGuest.getName());
            guest.setEmail(updatedGuest.getEmail());
            guest.setPhone(updatedGuest.getPhone());
            return guestRepository.save(guest);
        }).orElse(null);
    }

    public boolean deleteGuest(Long id) {
        if (guestRepository.existsById(id)) {
            guestRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

