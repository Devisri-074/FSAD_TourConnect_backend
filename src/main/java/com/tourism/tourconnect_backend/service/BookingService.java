package com.tourism.tourconnect_backend.service;

import com.tourism.tourconnect_backend.model.Booking;
import com.tourism.tourconnect_backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repository;

    // ✅ CREATE BOOKING
    public Booking create(Booking booking) {

        // 🔥 DEBUG (IMPORTANT)
        System.out.println("City: " + booking.getCity());
        System.out.println("Homestay: " + booking.getHomestayName());
        System.out.println("User: " + booking.getUserEmail());

        booking.setStatus("PENDING"); // default

        return repository.save(booking);
    }

    // ✅ GET ALL BOOKINGS
    public List<Booking> getAll() {
        return repository.findAll();
    }

    // ✅ GET HOST BOOKINGS
    public List<Booking> getByHost(Long hostId) {
        return repository.findByHostId(hostId);
    }

    // ✅ UPDATE STATUS (APPROVE / REJECT)
    public Booking updateStatus(Long id, String status) {
        Booking booking = repository.findById(id).orElseThrow();
        booking.setStatus(status);
        return repository.save(booking);
    }

    // ✅ DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }
}