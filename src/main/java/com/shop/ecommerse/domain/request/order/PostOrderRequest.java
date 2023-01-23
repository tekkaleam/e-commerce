package com.shop.ecommerse.domain.request.order;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PostOrderRequest {

    @NotBlank
    @Size(min = 3, max = 52)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String shipName;

    @NotBlank
    @Size(min = 3, max = 240)
    @Pattern(regexp = "[\\da-zA-Z #,-]+")
    private String shipAddress;

    @NotBlank
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String city;

   @NotBlank
    @Size(min = 3, max = 40)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String country;

    @NotBlank
    @Size(min = 11, max = 12)
    @Pattern(regexp = "\\d+")
    private String phone;

}
