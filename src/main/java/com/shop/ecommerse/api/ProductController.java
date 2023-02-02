package com.shop.ecommerse.api;

import com.shop.ecommerse.domain.response.product.ProductDetailsResponse;
import com.shop.ecommerse.domain.response.product.ProductResponse;
import com.shop.ecommerse.domain.response.product.ProductVariantResponse;
import com.shop.ecommerse.handler.exceptions.InvalidArgumentException;
import com.shop.ecommerse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class ProductController extends PublicApiController{

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/product")
    public ResponseEntity<List<ProductVariantResponse>> getAll(@RequestParam("page") Integer page,
                                                               @RequestParam("size") Integer pageSize,
                                                               @RequestParam(value = "sort", required = false) String sort,
                                                               @RequestParam(value = "category", required = false) String category,
                                                               @RequestParam(value = "minPrice", required = false) Float minPrice,
                                                               @RequestParam(value = "maxPrice", required = false) Float maxPrice){
        if (Objects.isNull(page) || page < 0) {
            throw new InvalidArgumentException("Invalid page");
        }
        if (Objects.isNull(pageSize) || pageSize < 0) {
            throw new InvalidArgumentException("Invalid pageSize");
        }
        List<ProductVariantResponse> responses = productService.getAll(page, pageSize, sort, category, minPrice, maxPrice);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(value = "/product/count")
    public ResponseEntity<Long> getAllCount(@RequestParam(value = "category", required = false) String category,
                                            @RequestParam(value = "minPrice", required = false) Float minPrice,
                                            @RequestParam(value = "maxPrice", required = false) Float maxPrice){
        Long count = productService.getAllCount(category, minPrice, maxPrice);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping(value = "/product/{url}")
    public ResponseEntity<ProductDetailsResponse> getByUrl(@PathVariable(name = "url") String url){
        if (url.isBlank()) {
            throw new InvalidArgumentException("Invalid url params");
        }
        ProductDetailsResponse response = productService.findByUrl(url);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(value = "/product/related/{url}")
    public ResponseEntity<List<ProductResponse>> getByRelated(@PathVariable("url") String url) {
        if (url.isBlank()) {
            throw new InvalidArgumentException("Invalid url params");
        }
        List<ProductResponse> products = productService.getRelatedProducts(url);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/product/recent")
    public ResponseEntity<List<ProductResponse>> getByNewlyAdded() {
        List<ProductResponse> products = productService.getNewlyAddedProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/product/mostselling")
    public ResponseEntity<List<ProductVariantResponse>> getByMostSelling() {
        List<ProductVariantResponse> products = productService.getMostSelling();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/product/interested")
    public ResponseEntity<List<ProductResponse>> getByInterested() {
        List<ProductResponse> products = productService.getInterested();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/product/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam("page") Integer page,
                                                               @RequestParam("size") Integer size,
                                                               @RequestParam("keyword") String keyword) {
        List<ProductResponse> products = productService.searchProductDisplay(keyword, page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}

