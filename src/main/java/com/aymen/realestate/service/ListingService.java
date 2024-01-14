package com.aymen.realestate.service;


import com.aymen.realestate.dto.ListingCreateRequest;
import com.aymen.realestate.dto.ListingUpdateRequest;
import com.aymen.realestate.model.Listing;
import com.aymen.realestate.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    public Listing createListing(ListingCreateRequest request) {
        Listing listing = new Listing();
        listing.setName(request.getName());
        listing.setDescription(request.getDescription());
        listing.setAddress(request.getAddress());
        listing.setBedrooms(request.getBedrooms());
        listing.setBathrooms(request.getBathrooms());
        listing.setPrice(request.getPrice());
        listing.setParking(request.isParking());
        listing.setFurnished(request.isFurnished());
        listing.setUserRef(request.getUserId());


        return listingRepository.save(listing);
    }

    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    public Listing updateListing(Long id, ListingUpdateRequest request) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        listing.setName(request.getName());
        listing.setDescription(request.getDescription());
        listing.setAddress(request.getAddress());
        listing.setBedrooms(request.getBedrooms());
        listing.setBathrooms(request.getBathrooms());
        listing.setPrice(request.getPrice());
        listing.setParking(request.isParking());
        listing.setFurnished(request.isFurnished());

        return listingRepository.save(listing);
    }

    public Listing getListing(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
    }

    public List<Listing> getListings() {
        return listingRepository.findAll();
    }


}