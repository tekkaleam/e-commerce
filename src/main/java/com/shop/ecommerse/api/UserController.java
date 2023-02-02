package com.shop.ecommerse.api;

import com.shop.ecommerse.domain.request.user.PasswordResetRequest;
import com.shop.ecommerse.domain.request.user.UpdateUserAddressRequest;
import com.shop.ecommerse.domain.response.user.UserResponse;
import com.shop.ecommerse.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController extends ApiController{

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/account")
    public ResponseEntity<UserResponse> getUser(){
        UserResponse response = userService.fetchUser();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping(value = "/account/address")
    public ResponseEntity<UserResponse> updateAddress(@RequestBody @Valid UpdateUserAddressRequest request){
        UserResponse response = userService.updateUserAddress(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping(value = "/account/password/reset")
    public ResponseEntity<HttpStatus> resetPassword(@RequestBody @Valid PasswordResetRequest request){
        userService.resetPassword(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/account/status")
    public ResponseEntity<Boolean> getVerificationStatus(){
        return new ResponseEntity<>(userService.getVerificationStatus(), HttpStatus.OK);
    }
}
