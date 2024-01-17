package com.aymen.realestate.service;

import com.aymen.realestate.config.JwtTokenProvider;
import com.aymen.realestate.dto.AuthenticationResult;
import com.aymen.realestate.model.User;
import com.aymen.realestate.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;


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


    public AuthenticationResult signIn(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {



            String token = jwtTokenProvider.generateToken(user);

            Cookie cookie = new Cookie("access_token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            user.setPassword("");

            return new AuthenticationResult(true, user, token, null, cookie);
        } else {
            return new AuthenticationResult(false, null, null, "Wrong credentials!", null);
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
