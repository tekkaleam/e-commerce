package com.shop.ecommerce.service;

import com.shop.ecommerce.converters.ProductCategoryResponseConverter;
import com.shop.ecommerce.domain.entity.ProductCategory;
import com.shop.ecommerce.domain.response.product.ProductCategoryResponse;
import com.shop.ecommerce.handler.exceptions.ResourceNotFoundException;
import com.shop.ecommerce.service.cache.ProductCategoryCacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceImplTest {

    @InjectMocks
    private ProductCategoryServiceImpl underTest;
    @Mock
    private ProductCategoryCacheService service;
    @Mock
    private ProductCategoryResponseConverter converter;

    @Test
    void isShouldFindAllByOrderByName() {
        //given
        List<ProductCategory> categories = Collections.singletonList(new ProductCategory());
        ProductCategoryResponse response = ProductCategoryResponse.builder().build();
        List<ProductCategoryResponse> expected = Collections.singletonList(response);

        given(service.findAllByOrderByName()).willReturn(categories);
        given(converter.apply(any(ProductCategory.class))).willReturn(response);

        // then
        then(underTest.findAllByOrderByName())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .isEqualTo(expected);
    }

    @Test
    void isShouldThrowExceptionIfCouldNotFoundProductCategories() {
        //given
        given(service.findAllByOrderByName()).willReturn(Collections.emptyList());

        //then
        assertThatThrownBy(() -> underTest.findAllByOrderByName())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Could not find product categories");
    }
}