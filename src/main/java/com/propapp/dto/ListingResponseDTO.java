package com.propapp.dto;

import com.propapp.model.Listing;
import java.time.LocalDate;

public class ListingResponseDTO {
    private String id;
    private String source;
    private String address;
    private String city;
    private String state;
    private Double price;
    private Integer bedrooms;
    private LocalDate listedDate;
    private double relevanceScore;

    // Constructor
    public ListingResponseDTO(Listing listing, double relevanceScore) {
        this.id = listing.getId();
        this.source = listing.getSource();
        this.address = listing.getAddress();
        this.city = listing.getCity();
        this.state = listing.getState();
        this.price = listing.getPrice();
        this.bedrooms = listing.getBedrooms();
        this.listedDate = listing.getListedDate();
        this.relevanceScore = Math.round(relevanceScore * 100.0) / 100.0;
    }

    // --- STANDARD JAVA GETTERS & SETTERS (Fixes compilation errors) ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }

    public LocalDate getListedDate() { return listedDate; }
    public void setListedDate(LocalDate listedDate) { this.listedDate = listedDate; }

    public double getRelevanceScore() { return relevanceScore; }
    public void setRelevanceScore(double relevanceScore) { this.relevanceScore = relevanceScore; }
}
