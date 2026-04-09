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
        h.setStatus("PENDING");
        h.setDefault(false);
        return repo.save(h);
    }

    public List<Homestay> getApproved() {
        return repo.findByStatus("APPROVED");
    }

    public List<Homestay> getAll() {
        return repo.findAll();
    }

    public Homestay approve(Long id) {
        Homestay h = repo.findById(id).orElseThrow();
        h.setStatus("APPROVED");
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