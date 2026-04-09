package com.tourism.tourconnect_backend.controller;

import com.tourism.tourconnect_backend.model.Homestay;
import com.tourism.tourconnect_backend.service.HomestayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homestays")
@CrossOrigin(origins = "http://localhost:5173")
public class HomestayController {

    @Autowired
    private HomestayService service;

    // Host adds
    @PostMapping
    public Homestay add(@RequestBody Homestay h) {
        return service.add(h);
    }

    // Tourist sees approved
    @GetMapping("/approved")
    public List<Homestay> getApproved() {
        return service.getApproved();
    }

    // Admin sees all
    @GetMapping
    public List<Homestay> getAll() {
        return service.getAll();
    }

    // Admin approve
    @PutMapping("/approve/{id}")
    public Homestay approve(@PathVariable Long id) {
        return service.approve(id);
    }

    // Admin delete (only non-default)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted";
    }
}