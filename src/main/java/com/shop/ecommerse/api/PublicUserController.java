package com.shop.ecommerse.api;


import com.shop.ecommerse.service.TokenService;
import com.shop.ecommerse.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicUserController extends PublicApiController{
    private final UserService userService;
    private final TokenService tokenService;

    public PublicUserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }
}
