package com.blog.controllers;

import com.blog.exceptions.LoginPasswordException;
import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.security.JwtTokenHelper;
import com.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService ;
    private final JwtTokenHelper jwtTokenHelper ;
    private final AuthenticationManager authenticationManager ;

    @Autowired
    public AuthenticationController(JwtTokenHelper jwtTokenHelper, AuthenticationManager authenticationManager , UserService userService){
        this.jwtTokenHelper = jwtTokenHelper ;
        this.authenticationManager = authenticationManager ;
        this.userService = userService ;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest){
        this.authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
        String token = jwtTokenHelper.generateToken(jwtAuthRequest.getUsername());
        return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.OK);
    }

    private void authenticate(String username , String password){
        try { // UsernamePasswordAuthenticationToken username va password ro mide be userDetailService va onja validate mikone
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (BadCredentialsException exception){ // incorrect password or username
            throw new LoginPasswordException(String.format("Invalid Password For User : %s",username));
        }
    }

    // Register New User
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto registeredUser = userService.registerUser(userDto);
        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }

}
