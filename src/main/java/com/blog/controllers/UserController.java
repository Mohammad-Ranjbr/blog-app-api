package com.blog.controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService ;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService ;
    }
    // POST - Create User
    @PostMapping("/")
    public ResponseEntity<UserDto> registerUser (@Valid @RequestBody UserDto userDto){
        UserDto registerUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(registerUserDto, HttpStatus.CREATED);
    }
    // PUT - Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer user_id){
        UserDto updatedUser = userService.updateUser(userDto,user_id);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }
    // DELETE - Delete User
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer user_id){
        userService.deleteUser(user_id);
        return new ResponseEntity<>(new ApiResponse(String.format("User With ID : %d Delete Successfully",user_id),true),HttpStatus.OK);
    }
    // GET - Get All Users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    // GET - Get User
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") int user_id){
        return new ResponseEntity<>(userService.getUserById(user_id),HttpStatus.OK);
    }

}
