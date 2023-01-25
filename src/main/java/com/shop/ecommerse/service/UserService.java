package com.shop.ecommerse.service;

import com.shop.ecommerse.domain.entity.User;
import com.shop.ecommerse.domain.request.user.PasswordResetRequest;
import com.shop.ecommerse.domain.request.user.RegisterUserRequest;
import com.shop.ecommerse.domain.request.user.UpdateUserAddressRequest;
import com.shop.ecommerse.domain.request.user.UpdateUserRequest;
import com.shop.ecommerse.domain.response.user.UserResponse;

public interface UserService {

    User register(RegisterUserRequest registerUserRequest);

    UserResponse fetchUser();

    User getUser();

    User saveUser(User user);

    User findByEmail(String email);

    boolean userExists(String email);

    UserResponse updateUser(UpdateUserRequest updateUserRequest);

    UserResponse updateUserAddress(UpdateUserAddressRequest updateUserAddressRequest);

    void resetPassword(PasswordResetRequest passwordResetRequest);

    Boolean getVerificationStatus();
}
