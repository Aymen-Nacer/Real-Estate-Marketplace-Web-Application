package com.aymen.realestate.repository;

import com.aymen.realestate.model.Listing;
import com.aymen.realestate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
