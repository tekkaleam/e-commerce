package com.shop.ecommerce.service;

import com.shop.ecommerce.domain.response.product.ProductCategoryResponse;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategoryResponse> findAllByOrderByName();

}