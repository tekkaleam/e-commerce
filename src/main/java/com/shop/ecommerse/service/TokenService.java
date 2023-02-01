package com.shop.ecommerse.service;

import com.shop.ecommerse.domain.entity.User;
import com.shop.ecommerse.domain.request.user.PasswordForgotValidateRequest;

public interface TokenService {

    void createEmailConfirmToken(User user);

    void createPasswordResetToken(String email);

    void validateEmail(String token);

    void validateForgotPasswordConfirm(String token);

    void validateForgotPassword(PasswordForgotValidateRequest passwordForgotValidateRequest);
}
