package com.shop.ecommerce.service;

import com.github.javafaker.Faker;
import com.shop.ecommerce.converters.ProductDetailsResponseConverter;
import com.shop.ecommerce.converters.ProductResponseConverter;
import com.shop.ecommerce.converters.ProductVariantResponseConverter;
import com.shop.ecommerce.domain.entity.Product;
import com.shop.ecommerce.domain.entity.ProductCategory;
import com.shop.ecommerce.domain.entity.ProductVariant;
import com.shop.ecommerce.domain.response.product.ProductDetailsResponse;
import com.shop.ecommerce.domain.response.product.ProductResponse;
import com.shop.ecommerce.domain.response.product.ProductVariantResponse;
import com.shop.ecommerce.handler.exceptions.InvalidArgumentException;
import com.shop.ecommerce.handler.exceptions.ResourceNotFoundException;
import com.shop.ecommerce.repository.ProductRepository;
import com.shop.ecommerce.repository.ProductVariantRepository;
import com.shop.ecommerce.service.cache.ProductCacheService;
import com.shop.ecommerce.service.cache.ProductVariantCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl underTest;

    @Mock
    private ProductCacheService productCacheService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductVariantRepository productVariantRepository;

    @Mock
    private ProductVariantCacheService productVariantCacheService;

    @Mock
    private ProductResponseConverter productResponseConverter;

    @Mock
    private ProductVariantResponseConverter productVariantResponseConverter;

    @Mock
    private ProductDetailsResponseConverter productDetailsResponseConverter;

    private Faker faker;


    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }


    @Test
    void isShouldFindByUrl() {
        // given
        String url = faker.internet().domainSuffix();

        Product product = new Product();

        ProductDetailsResponse productDetailsResponseExpected = new ProductDetailsResponse();

        given(productCacheService.findByUrl(url)).willReturn(product);
        given(productDetailsResponseConverter.apply(product)).willReturn(productDetailsResponseExpected);

        // when
        ProductDetailsResponse productDetailsResponseResult = underTest.findByUrl(url);

        // then
        verify(productDetailsResponseConverter).apply(product);
        then(productDetailsResponseResult).isEqualTo(productDetailsResponseExpected);
    }

    @Test
    void isShouldThrowExceptionIfNotFoundByUrl() {
        String url = faker.internet().domainSuffix();

        given(productCacheService.findByUrl(url)).willReturn(null);
        // then
        assertThatThrownBy(() -> underTest.findByUrl(url))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Could not find this product");
        verify(productCacheService).findByUrl(url);

    }

    @Test
    void isShouldGetAll() {
        Integer page = faker.number().randomDigitNotZero();
        Integer size = faker.number().randomDigitNotZero();
        String sort = faker.bool().bool() ? "lowest" : "highest";
        String category = faker.lorem().word();
        float minPrice = (float) faker.number().randomNumber();
        float maxPrice = minPrice + (float) faker.number().randomNumber();

        ProductVariant productVariant = new ProductVariant();
        List<ProductVariant> productVariantList = new ArrayList<>();
        productVariantList.add(productVariant);

        Page<ProductVariant> productVariantPage = new PageImpl<>(productVariantList);

        ProductVariantResponse productVariantResponseExpected = ProductVariantResponse.builder().build();

        given(productVariantRepository.findAll(any(Specification.class), any(PageRequest.class))).willReturn(productVariantPage);
        given(productVariantResponseConverter.apply(any(ProductVariant.class))).willReturn(productVariantResponseExpected);

        // when
        List<ProductVariantResponse> productVariantResponseList = underTest.getAll(page, size, sort, category, minPrice, maxPrice);

        // then
        then(productVariantResponseList.size()).isEqualTo(productVariantList.size());
        productVariantResponseList.forEach(productVariantResponse -> then(productVariantResponse).isEqualTo(productVariantResponseExpected));
    }

    @Test
    void isShouldGetAllWithoutSort() {
        Integer page = faker.number().randomDigitNotZero();
        Integer size = faker.number().randomDigitNotZero();
        String category = faker.lorem().word();
        float minPrice = (float) faker.number().randomNumber();
        Float maxPrice = minPrice + (float) faker.number().randomNumber();

        ProductVariant productVariant = new ProductVariant();
        List<ProductVariant> productVariantList = new ArrayList<>();
        productVariantList.add(productVariant);

        Page<ProductVariant> productVariantPage = new PageImpl<>(productVariantList);

        ProductVariantResponse productVariantResponseExpected = ProductVariantResponse.builder().build();

        given(productVariantRepository.findAll(any(Specification.class), any(PageRequest.class))).willReturn(productVariantPage);
        given(productVariantResponseConverter.apply(any(ProductVariant.class))).willReturn(productVariantResponseExpected);

        // when
        List<ProductVariantResponse> productVariantResponseList = underTest.getAll(page, size, null,category, minPrice, maxPrice);

        // then
        then(productVariantResponseList.size()).isEqualTo(productVariantList.size());
        productVariantResponseList.forEach(productVariantResponse -> then(productVariantResponse).isEqualTo(productVariantResponseExpected));
    }

    @Test
    void isShouldThrowExceptionOnInvalidSortInGetAll() {
        Integer page = faker.number().randomDigitNotZero();
        Integer size = faker.number().randomDigitNotZero();
        String category = faker.lorem().word();
        String sort = faker.lorem().word();
        Float minPrice = (float) faker.number().randomNumber();
        Float maxPrice = minPrice + (float) faker.number().randomNumber();

        ProductVariant productVariant = new ProductVariant();
        List<ProductVariant> productVariantList = new ArrayList<>();
        productVariantList.add(productVariant);

        assertThatThrownBy(() -> underTest.getAll(page, size, sort,category, minPrice, maxPrice))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Invalid sort parameter");
    }

    @Test
    void isShouldGetAllCount() {
        String category = faker.lorem().word();
        Float minPrice = (float) faker.number().randomDigitNotZero();
        Float maxPrice = (float) faker.number().randomDigitNotZero();
        Long l = (long) faker.number().numberBetween(2, 1000);
        given(productVariantRepository.count(any(Specification.class))).willReturn(l);

        then(underTest.getAllCount(category, minPrice, maxPrice))
                .isInstanceOf(Long.class)
                .isGreaterThan(0);
    }

    @Test
    void isShouldFindProductVariantById() {
        // given
        ProductVariant expected = new ProductVariant();
        Long id = faker.number().randomNumber();
        given(productVariantCacheService.findById(id)).willReturn(expected);
        
        ProductVariant real = underTest.findProductVariantById(id);
        // then
        verify(productVariantCacheService).findById(id);
        then(real).isNotNull().isEqualTo(expected);
    }

    @Test
    void isShouldThrowExceptionIfFindByIdReturnNull() {
        Long id = faker.number().randomNumber();
        given(productVariantCacheService.findById(id)).willReturn(null);

        assertThatThrownBy(() -> underTest.findProductVariantById(id))
               .isInstanceOf(ResourceNotFoundException.class)
               .hasMessageContaining(String.format("Could not find any product variant with the id %d", id));
    }

    @Test
    void isShouldGetRelatedProducts() {
        // given
        Product product = new Product();
        product.setProductCategory(ProductCategory.builder().name(faker.lorem().word()).build());
        String url = faker.internet().url();
        List<Product> products = Collections.singletonList(product);
        ProductResponse ex = new ProductResponse();

        given(productCacheService.findByUrl(url)).willReturn(product);
        given(productCacheService.getRelatedProducts(product.getProductCategory(), product.getId())).willReturn(products);
        given(productResponseConverter.apply(any(Product.class))).willReturn(ex);

        // when
        List<ProductResponse> productResponses = underTest.getRelatedProducts(url);

        // then
        verify(productCacheService).findByUrl(url);
        verify(productCacheService).getRelatedProducts(product.getProductCategory(), product.getId());
        then(productResponses)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void isShouldThrowExceptionIfGetRelatedNotFoundRelated() {
        String url = faker.internet().url();

        given(productCacheService.findByUrl(url)).willReturn(null);

        //then
        assertThatThrownBy(() -> underTest.getRelatedProducts(url))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Related products not found");
    }

    @Test
    void isShouldGetNewlyAddedProducts() {
        // given
        List<ProductResponse> expected = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        ProductResponse response = new ProductResponse();

        for(int i = 0; i < 10; i++) {
            expected.add(new ProductResponse());
            if(i <8) products.add(new Product());
        }

        given(productCacheService.findTop8ByOrderByDateCreatedDesc()).willReturn(products);
        given(productResponseConverter.apply(any(Product.class))).willReturn(response);

        // then

        then(underTest.getNewlyAddedProducts())
                .isNotEmpty()
                .hasSize(8);
    }

    @Test
    void isThrowExceptionIdNewlyAddedNotFound() {
        given(productCacheService.findTop8ByOrderByDateCreatedDesc()).willReturn(Collections.emptyList());

        //then
        assertThatThrownBy(() -> underTest.getNewlyAddedProducts())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Newly added products not found");
    }

    @Test
    void isShouldGetMostSelling() {
        // given
        List<ProductResponse> expected = new ArrayList<>();
        List<ProductVariant> products = new ArrayList<>();
        ProductVariantResponse response = ProductVariantResponse.builder().build();

        for(int i = 0; i < 10; i++) {
            expected.add(new ProductResponse());
            if(i <8) products.add(new ProductVariant());
        }

        given(productVariantCacheService.findTop8ByOrderBySellCountDesc()).willReturn(products);
        given(productVariantResponseConverter.apply(any(ProductVariant.class))).willReturn(response);

        // then

        then(underTest.getMostSelling())
                .isNotEmpty()
                .hasSize(8);
    }

    @Test
    void isThrowExceptionIfMostSellingNotFound() {
        // given
        given(productVariantCacheService.findTop8ByOrderBySellCountDesc()).willReturn(Collections.emptyList());

        //then
        assertThatThrownBy(() -> underTest.getMostSelling())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Most selling products not found");
    }

    @Test
    void isShouldGetInterested() {
        // given
        List<ProductResponse> expected = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        ProductResponse response = new ProductResponse();

        for(int i = 0; i < 10; i++) {
            expected.add(new ProductResponse());
            if(i <8) products.add(new Product());
        }

        given(productCacheService.findTop8ByOrderByDateCreatedDesc()).willReturn(products);
        given(productResponseConverter.apply(any(Product.class))).willReturn(response);

        // then

        then(underTest.getInterested())
                .isNotEmpty()
                .hasSize(8);
    }

    @Test
    void isShouldThrowExceptionIfInterestedNotFound() {
        // given
        given(productCacheService.findTop8ByOrderByDateCreatedDesc()).willReturn(Collections.emptyList());

        //then
        assertThatThrownBy(() -> underTest.getInterested())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Interested products not found");
    }

    @Test
    void isShouldSearchProductDisplay() {
        // given
        String keyword = faker.lorem().word();
        int page = faker.number().randomDigitNotZero();
        int size = faker.number().randomDigitNotZero();
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Product> products = Collections.singletonList(new Product());
        ProductResponse response = new ProductResponse();
        List<ProductResponse> expected = Collections.singletonList(response);

        given(productRepository.findAllByNameContainingIgnoreCase(keyword, pageRequest)).willReturn(products);
        given(productResponseConverter.apply(any(Product.class))).willReturn(response);
        //then
        then(underTest.searchProductDisplay(keyword, page, size))
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expected);
    }

    @Test
    void isShouldThrowExceptionIfSizeOrPageInSearchDisplayNull() {

        // when, then
        assertThatThrownBy(() -> underTest.searchProductDisplay(faker.lorem().word(), null, faker.number().randomDigitNotZero()))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Page and size are required");

        assertThatThrownBy(() -> underTest.searchProductDisplay(faker.lorem().word(), faker.number().randomDigitNotZero(), null))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Page and size are required");
    }


}