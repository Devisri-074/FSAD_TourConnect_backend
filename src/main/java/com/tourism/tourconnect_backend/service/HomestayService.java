package com.tourism.tourconnect_backend.service;

import com.tourism.tourconnect_backend.model.Homestay;
import com.tourism.tourconnect_backend.repository.HomestayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomestayService {

    @Autowired
    private HomestayRepository repo;

    public Homestay add(Homestay h) {
        // Only default to PENDING if no status provided
        if (h.getStatus() == null || h.getStatus().isEmpty()) {
            h.setStatus("PENDING");
            h.setApprovalStatus("PENDING");
        } else {
            h.setStatus(h.getStatus().toUpperCase());
            h.setApprovalStatus(h.getStatus().toUpperCase());
        }
        h.setDefault(false);
        // Sync name and title
        if (h.getName() == null && h.getTitle() != null) h.setName(h.getTitle());
        if (h.getTitle() == null && h.getName() != null) h.setTitle(h.getName());
        return repo.save(h);
    }

    public List<Homestay> getApproved() {
        return repo.findByStatus("APPROVED");
    }

    public List<Homestay> getAll() {
        return repo.findAll();
    }

    public Homestay updateStatus(Long id, String status) {
        Homestay h = repo.findById(id).orElseThrow();
        h.setStatus(status.toUpperCase());
        h.setApprovalStatus(status.toUpperCase());
        if (status.equalsIgnoreCase("APPROVED")) {
            // ensure name is synced
            if (h.getName() == null && h.getTitle() != null) h.setName(h.getTitle());
        }
        return repo.save(h);
    }

    public Homestay approve(Long id) {
        Homestay h = repo.findById(id).orElseThrow();
        h.setStatus("APPROVED");
        h.setApprovalStatus("APPROVED");
        return repo.save(h);
    }

    public void delete(Long id) {
        Homestay h = repo.findById(id).orElseThrow();

        if (!h.isDefault()) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Cannot delete default homestay");
        }
    }
}