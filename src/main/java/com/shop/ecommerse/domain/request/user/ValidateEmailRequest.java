package com.shop.ecommerse.domain.request.user;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ValidateEmailRequest {

    @NotBlank
    String token;
}
