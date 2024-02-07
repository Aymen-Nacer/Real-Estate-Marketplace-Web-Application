package com.aymen.realestate.service;

import com.aymen.realestate.config.JwtTokenProvider;
import com.aymen.realestate.dto.ApiResponse;
import com.aymen.realestate.dto.SignInResponse;
import com.aymen.realestate.model.User;
import com.aymen.realestate.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;
import org.apache.commons.lang3.RandomStringUtils;



@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;




    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void signUp(String username, String email, String password) {


        try {
            String hashedPassword = passwordEncoder.encode(password);


            User user = new User(username, email, hashedPassword);

            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error during user registration in service", e);
        }
    }


    public SignInResponse signIn(String email, String password) {

        User user = userRepository.findByEmail(email);



        if (user != null && passwordEncoder.matches(password, user.getPassword())) {


            String token = jwtTokenProvider.generateToken(user);


            Cookie cookie = new Cookie("access_token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            user.setPassword("");




            return new SignInResponse(true, "success creating cookie",  user , cookie);
        } else {
            return new SignInResponse(false, "Wrong credentials!",  null , null);
        }
    }

    public SignInResponse googleAuth(String email, String username, String avatar) {
        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            String token = jwtTokenProvider.generateToken(existingUser);


            Cookie cookie = new Cookie("access_token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            existingUser.setPassword(null);


            return new SignInResponse(true, "success creating cookie", existingUser, cookie);
        } else {

            String generatedPassword = RandomStringUtils.random(8, true, true);



            String hashedPassword = passwordEncoder.encode(generatedPassword);

            User newUser = new User(username, email, hashedPassword, avatar);

            userRepository.save(newUser);

            String token = jwtTokenProvider.generateToken(newUser);


            Cookie cookie = new Cookie("access_token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");


            System.out.println("####### user doesn't exist details are: " + newUser.toString());


            newUser.setPassword(null);

            return new SignInResponse(true, "success creating cookie", newUser, cookie);

        }
    }

    public void signOut(HttpServletResponse response) {
        Cookie cookie = new Cookie("access_token", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
