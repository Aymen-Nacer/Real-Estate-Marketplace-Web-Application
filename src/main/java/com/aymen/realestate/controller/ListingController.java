package com.aymen.realestate.controller;


import com.aymen.realestate.dto.ListingCreateRequest;
import com.aymen.realestate.dto.ListingGetQueryRequest;
import com.aymen.realestate.dto.ListingUpdateRequest;
import com.aymen.realestate.model.Listing;
import com.aymen.realestate.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    private static final Logger logger = LoggerFactory.getLogger(ListingController.class);


    @PostMapping("/create")
    public ResponseEntity<Listing> createListing(@RequestBody ListingCreateRequest request) {
        try {
            System.out.println("###########################################");
            System.out.println("received request is: " + request.toString());
            System.out.println("###########################################");
            Listing createdListing = listingService.createListing(request);
            return ResponseEntity.ok(createdListing);
        } catch (Exception e) {
            logger.error("Error creating listing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteListing(@PathVariable Long id) {
        try {
            listingService.deleteListing(id);
            return ResponseEntity.ok("Listing deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting listing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting listing");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable Long id, @RequestBody ListingUpdateRequest request) {
        try {
            Listing updatedListing = listingService.updateListing(id, request);
            return ResponseEntity.ok(updatedListing);
        } catch (Exception e) {
            logger.error("Error updating listing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Listing> getListing(@PathVariable Long id) {
        try {
            Listing listing = listingService.getListing(id);
            return ResponseEntity.ok(listing);
        } catch (Exception e) {
            logger.error("Error getting listing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Page<Listing>> searchAndFilterListings(  @RequestParam(required = false) String searchTerm,
                                                                   @RequestParam(required = false) Boolean furnished,
                                                                   @RequestParam(required = false) Boolean parking,
                                                                   @RequestParam(required = false) String sort,
                                                                   @RequestParam(required = false) String order,
                                                                   @RequestParam(required = false) Integer limit,
                                                                   @RequestParam(required = false) Integer startIndex) {


        System.out.println("Search term received" + searchTerm);
        System.out.println("furnished received" + furnished);

        ListingGetQueryRequest request = new ListingGetQueryRequest();

        try {
            boolean[] furnishedValues;
            boolean[] parkingValues;

            if (limit == null || limit <= 0) {
                request.setLimit(9);
            }

            if (startIndex == null || startIndex < 0) {
                request.setStartIndex(0);
            }


            if (furnished == null || !furnished) {
                furnishedValues = new boolean[]{true, false};
            } else {
                furnishedValues = new boolean[]{true};
            }

            if (parking == null || !parking) {
                parkingValues = new boolean[]{true, false};
            } else {
                parkingValues = new boolean[]{true};
            }

            if (searchTerm == null) {
                request.setSearchTerm("");
            }
            else {
                request.setSearchTerm(searchTerm);
            }

            if (sort == null) {
                request.setSort("createdAt");
            }

            if (order == null) {
                request.setOrder("desc");
            }
            System.out.println("#########################");
            System.out.println("request after setting is "  + request.toString());
            System.out.println("#########################");
            System.out.println("furnished values is" + Arrays.toString(furnishedValues));
            System.out.println("parking values is" + Arrays.toString(parkingValues));



            Page<Listing> searchResults = listingService.searchAndFilterListings(request, furnishedValues, parkingValues);
            return ResponseEntity.ok(searchResults);
        } catch (Exception e) {
            logger.error("Error searching and filtering listings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }

}