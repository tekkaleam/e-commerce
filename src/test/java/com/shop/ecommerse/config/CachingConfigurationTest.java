package com.shop.ecommerse.config;

import com.github.benmanes.caffeine.cache.Ticker;
import com.shop.ecommerse.config.cache.CachingConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ComponentScan(basePackages = {"com.shop.ecommerse.constrains"})
public class CachingConfigurationTest {

    @Autowired
    private CachingConfiguration cachingConfiguration;

    @Autowired
    private Ticker ticker;

    @Test
    void itShouldLoadRegisteredCacheNames() {

        // when
        Collection<String> cacheNames = cachingConfiguration.cacheManager(ticker).getCacheNames();

        // then
        assertThat(cacheNames)
                .hasSize(4)
                .containsExactly(
                "product",
                "product_variant",
                "product_category",
                "product_color"
        );
    }
}
