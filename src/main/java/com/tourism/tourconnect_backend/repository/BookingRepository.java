package com.tourism.tourconnect_backend.repository;

import com.tourism.tourconnect_backend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByHostId(Long hostId);

    List<Booking> findByUserEmail(String userEmail);
}