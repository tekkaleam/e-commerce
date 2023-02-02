package com.shop.ecommerce.service;

import com.shop.ecommerce.domain.entity.User;
import com.shop.ecommerce.domain.request.user.PasswordResetRequest;
import com.shop.ecommerce.domain.request.user.RegisterUserRequest;
import com.shop.ecommerce.domain.request.user.UpdateUserAddressRequest;
import com.shop.ecommerce.domain.request.user.UpdateUserRequest;
import com.shop.ecommerce.domain.response.user.UserResponse;

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
