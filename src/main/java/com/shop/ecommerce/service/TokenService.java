package com.shop.ecommerce.service;

import com.shop.ecommerce.domain.entity.User;
import com.shop.ecommerce.domain.request.user.PasswordForgotValidateRequest;

public interface TokenService {

    void createEmailConfirmToken(User user);

    void createPasswordResetToken(String email);

    void validateEmail(String token);

    void validateForgotPasswordConfirm(String token);

    void validateForgotPassword(PasswordForgotValidateRequest passwordForgotValidateRequest);
}
