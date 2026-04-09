package com.tourism.tourconnect_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String homestayName;
    private String guideName;

    private String userEmail;

    private String startDate;
    private String endDate;

    private Double homestayPrice;
    private Double guidePrice;

    private String status;

    private Long hostId;

    // ✅ GETTERS & SETTERS

    public Long getId() { return id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getHomestayName() { return homestayName; }
    public void setHomestayName(String homestayName) { this.homestayName = homestayName; }

    public String getGuideName() { return guideName; }
    public void setGuideName(String guideName) { this.guideName = guideName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public Double getHomestayPrice() { return homestayPrice; }
    public void setHomestayPrice(Double homestayPrice) { this.homestayPrice = homestayPrice; }

    public Double getGuidePrice() { return guidePrice; }
    public void setGuidePrice(Double guidePrice) { this.guidePrice = guidePrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getHostId() { return hostId; }
    public void setHostId(Long hostId) { this.hostId = hostId; }
}