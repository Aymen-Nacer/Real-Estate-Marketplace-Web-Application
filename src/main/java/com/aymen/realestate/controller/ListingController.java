package com.aymen.realestate.controller;


import com.aymen.realestate.dto.ListingCreateRequest;
import com.aymen.realestate.dto.ListingUpdateRequest;
import com.aymen.realestate.model.Listing;
import com.aymen.realestate.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @PostMapping("/create")
    public ResponseEntity<Listing> createListing(@RequestBody ListingCreateRequest request) {
        Listing createdListing = listingService.createListing(request);
        return ResponseEntity.ok(createdListing);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteListing(@PathVariable Long id) {
        listingService.deleteListing(id);
        return ResponseEntity.ok("Listing deleted successfully");
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable Long id, @RequestBody ListingUpdateRequest request) {
        Listing updatedListing = listingService.updateListing(id, request);
        return ResponseEntity.ok(updatedListing);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Listing> getListing(@PathVariable Long id) {
        Listing listing = listingService.getListing(id);
        return ResponseEntity.ok(listing);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Listing>> getListings() {
        List<Listing> listings = listingService.getListings();
        return ResponseEntity.ok(listings);
    }



}