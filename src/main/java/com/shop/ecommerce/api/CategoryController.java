package com.shop.ecommerce.api;

import com.shop.ecommerce.domain.response.product.ProductCategoryResponse;
import com.shop.ecommerce.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final ProductCategoryService categoryService;

    @Autowired
    public CategoryController(ProductCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/category")
    public ResponseEntity<List<ProductCategoryResponse>> getAllCategories(){
        List<ProductCategoryResponse> responseList = categoryService.findAllByOrderByName();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
