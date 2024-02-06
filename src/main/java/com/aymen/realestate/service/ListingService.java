package com.aymen.realestate.service;


import com.aymen.realestate.dto.ListingCreateRequest;
import com.aymen.realestate.dto.ListingGetQueryRequest;
import com.aymen.realestate.dto.ListingUpdateRequest;
import com.aymen.realestate.model.Listing;
import com.aymen.realestate.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    public Listing createListing(ListingCreateRequest request) {
        Listing listing = new Listing();

        if (request.getName() != null) {
            listing.setName(request.getName());
        }

        if (request.getDescription() != null) {
            listing.setDescription(request.getDescription());
        }

        if (request.getAddress() != null) {
            listing.setAddress(request.getAddress());
        }

        if (request.getBedrooms() != null) {
            listing.setBedrooms(request.getBedrooms());
        }

        if (request.getBathrooms() != null) {
            listing.setBathrooms(request.getBathrooms());
        }

        if (request.getPrice() != null) {
            listing.setPrice(request.getPrice());
        }

        if (request.isParking() != null) {
            listing.setParking(request.isParking());
        }

        if (request.isFurnished() != null) {
            listing.setFurnished(request.isFurnished());
        }

        if (request.getUserId() != null) {
            listing.setUserId(request.getUserId());
        }

        if (request.getImageUrls() != null) {
            listing.setImageUrls(request.getImageUrls());
        }


        return listingRepository.save(listing);
    }

    public void deleteListing(Long id) {
        listingRepository.deleteById(id);
    }

    public Listing updateListing(Long id, ListingUpdateRequest request) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        // Check if request attributes are not null before updating
        if (request.getName() != null) {
            listing.setName(request.getName());
        }

        if (request.getDescription() != null) {
            listing.setDescription(request.getDescription());
        }

        if (request.getAddress() != null) {
            listing.setAddress(request.getAddress());
        }

        if (request.getBedrooms() != null) {
            listing.setBedrooms(request.getBedrooms());
        }

        if (request.getBathrooms() != null) {
            listing.setBathrooms(request.getBathrooms());
        }

        if (request.getPrice() != null) {
            listing.setPrice(request.getPrice());
        }

        if (request.isParking() != null) {
            listing.setParking(request.isParking());
        }

        if (request.isFurnished() != null) {
            listing.setFurnished(request.isFurnished());
        }

        if (request.getUrls() != null) {
            listing.setImageUrls(request.getUrls());
        }

        return listingRepository.save(listing);
    }

    public Listing getListing(Long id) {


        return listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
    }


    public Listing[] searchAndFilterListings(ListingGetQueryRequest request, boolean[] furnishedValues, boolean[] parkingValues) {


        Sort sort = Sort.by(request.getOrder().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, request.getSort());
        int startIndex = Math.max(request.getStartIndex(), 0);
        int pageSize = Math.max(request.getLimit(), 1);


        int pageIndex = startIndex / pageSize;
        int pageOffset = startIndex % pageSize;

        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        Page<Listing> pageResult = listingRepository.findByNameContainingIgnoreCaseAndFurnishedInAndParkingIn(
                request.getSearchTerm(), furnishedValues, parkingValues, pageable
        );

        List<Listing> listings = pageResult.getContent().subList(pageOffset, pageResult.getContent().size());

        return listings.toArray(new Listing[0]);
    }
}
