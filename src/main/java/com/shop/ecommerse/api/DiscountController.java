package com.shop.ecommerse.api;

import com.shop.ecommerse.domain.request.discount.ApplyDiscountRequest;
import com.shop.ecommerse.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class DiscountController extends ApiController{

    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping(value = "/cart/discount")
    public ResponseEntity<HttpStatus> applyDiscount(@RequestBody @Valid ApplyDiscountRequest request){
        discountService.applyDiscount(request.getCode());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
