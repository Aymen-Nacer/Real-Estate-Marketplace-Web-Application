package com.aymen.realestate.repository;

import com.aymen.realestate.model.Listing;
import com.aymen.realestate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByUserRef(String userId);

}