package com.shop.ecommerse.domain.request.user;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordForgotConfirmRequest {
    @NotBlank
    String token;
}