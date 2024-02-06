package com.aymen.realestate.controller;

import com.aymen.realestate.config.JwtTokenProvider;
import com.aymen.realestate.dto.ApiResponse;
import com.aymen.realestate.dto.ListingCreateRequest;
import com.aymen.realestate.dto.ListingGetQueryRequest;
import com.aymen.realestate.dto.ListingUpdateRequest;
import com.aymen.realestate.model.Listing;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aymen.realestate.service.ListingService;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(ListingController.class);

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createListing(@RequestBody ListingCreateRequest request,
                                           @CookieValue(name = "access_token") String accessTokenCookie) {
        try {
            Jws<Claims> claimsJws = jwtTokenProvider.validateToken(accessTokenCookie);
            Listing createdListing = listingService.createListing(request);
            return ResponseEntity.ok(new ApiResponse(true, null, List.of(createdListing)  ,  "Listing created successfully"));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false, null, null, "Token is not valid"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, null, null,  "Error creating listing"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteListing(@PathVariable Long id,
                                           @CookieValue(name = "access_token") String accessTokenCookie) {
        try {
            Jws<Claims> claimsJws = jwtTokenProvider.validateToken(accessTokenCookie);
            listingService.deleteListing(id);
            return ResponseEntity.ok(new ApiResponse(true, null, null,  "Listing deleted successfully"));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false, null, null, "Token is not valid"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, null, null, "Error deleting listing"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateListing(@PathVariable Long id,
                                           @RequestBody ListingUpdateRequest request,
                                           @CookieValue(name = "access_token") String accessTokenCookie) {
        try {
            Jws<Claims> claimsJws = jwtTokenProvider.validateToken(accessTokenCookie);
            Listing updatedListing = listingService.updateListing(id, request);
            return ResponseEntity.ok(new ApiResponse(true, null, List.of(updatedListing), "Listing updated successfully"));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false, null, null, "Token is not valid"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, null, null, "Error updating listing"));
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getListing(@PathVariable Long id) {
        try {
            Listing listing = listingService.getListing(id);
            return ResponseEntity.ok(new ApiResponse(true,null, List.of(listing), "Listing retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, null, null, "Error getting listing"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> searchAndFilterListings(@RequestParam(required = false) String searchTerm,
                                                     @RequestParam(required = false) Boolean furnished,
                                                     @RequestParam(required = false) Boolean parking,
                                                     @RequestParam(required = false) String sort,
                                                     @RequestParam(required = false) String order,
                                                     @RequestParam(required = false) Integer limit,
                                                     @RequestParam(required = false) Integer startIndex
                                                   ) {

        ListingGetQueryRequest request = new ListingGetQueryRequest();

        try {

            boolean[] furnishedValues;
            boolean[] parkingValues;

            if (limit == null) {
                request.setLimit(9);
            } else {
                request.setLimit(limit);
            }

            if (startIndex == null || startIndex < 0) {
                request.setStartIndex(0);
            } else {
                request.setStartIndex(startIndex);
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
            } else {
                request.setSearchTerm(searchTerm);
            }

            if (sort == null) {
                request.setSort("createdAt");
            } else {
                request.setSort(sort);
            }

            if (order == null) {
                request.setOrder("desc");
            } else {
                request.setOrder(order);
            }

            System.out.println("#######################");
            System.out.println("request is "  + request.toString());

            System.out.println("#######################");


            Listing[] searchResults = listingService.searchAndFilterListings(request, furnishedValues, parkingValues);
            return ResponseEntity.ok(new ApiResponse(true, null,  List.of(searchResults), "Listings retrieved successfully"));

        } catch (Exception e) {
            logger.error("Error searching and filtering listings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, null, null,  "Error searching and filtering listings"));
        }
    }
}
