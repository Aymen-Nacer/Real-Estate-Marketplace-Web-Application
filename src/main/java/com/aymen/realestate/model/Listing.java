package com.aymen.realestate.model;


import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @Column(name = "regular_price", nullable = false)
    private Double regularPrice;

    @Column(name = "discount_price", nullable = false)
    private Double discountPrice;

    @Column(nullable = false)
    private Integer bathrooms;

    @Column(nullable = false)
    private Integer bedrooms;

    @Column(nullable = false)
    private Boolean furnished;

    @Column(nullable = false)
    private Boolean parking;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Boolean offer;

    @ElementCollection
    @CollectionTable(name = "listing_image_urls", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @Column(name = "user_ref", nullable = false)
    private String userRef;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Boolean getFurnished() {
        return furnished;
    }

    public void setFurnished(Boolean furnished) {
        this.furnished = furnished;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getOffer() {
        return offer;
    }

    public void setOffer(Boolean offer) {
        this.offer = offer;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getUserRef() {
        return userRef;
    }

    public void setUserRef(String userRef) {
        this.userRef = userRef;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}