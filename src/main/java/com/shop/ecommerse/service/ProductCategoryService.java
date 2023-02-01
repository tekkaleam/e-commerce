package com.shop.ecommerse.service;

import com.shop.ecommerse.domain.response.product.ProductCategoryResponse;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategoryResponse> findAllByOrderByName();

}