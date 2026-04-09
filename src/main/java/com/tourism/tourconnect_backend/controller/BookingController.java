package com.tourism.tourconnect_backend.controller;

import com.tourism.tourconnect_backend.model.Booking;
import com.tourism.tourconnect_backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    @Autowired
    private BookingService service;

    // ✅ CREATE BOOKING
    @PostMapping
    public Booking create(@RequestBody Booking b) {
        return service.create(b);
    }

    // ✅ GET ALL BOOKINGS
    @GetMapping
    public List<Booking> getAll() {
        return service.getAll();
    }

    // ✅ GET BOOKINGS FOR HOST
    @GetMapping("/host/{hostId}")
    public List<Booking> getHostBookings(@PathVariable Long hostId) {
        return service.getByHost(hostId);
    }

    // ✅ UPDATE STATUS
    @PutMapping("/{id}/status")
    public Booking updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted";
    }
}