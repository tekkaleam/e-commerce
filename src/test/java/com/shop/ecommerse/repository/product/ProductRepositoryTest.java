package com.shop.ecommerse.repository.product;

import com.shop.ecommerse.domain.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository underTest;
    @Test
    void isShouldSaveProduct() {
        Product product = underTest.save(Product.builder()
                .amount(2)
                .name("Cikle")
                .price(1000F)
                .build());

        assertThat(underTest.findById(product.getId()))
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c).isEqualTo(product).usingRecursiveComparison());
    }
    @Test
    void isShouldDeleteProduct() {
        Product product = underTest.save(Product.builder()
                .amount(2)
                .name("Cikle")
                .price(1000F)
                .build());
        Product product1 = underTest.save(Product.builder()
                .amount(1)
                .name("Clock")
                .price(1500F)
                .build());

        int i = underTest.delete(product1.getId());

        List<Product> test = underTest.findAll();

        assertThat(test)
                .hasSize(1)
                        .contains(product);

        assertThat(underTest.findByName(product1.getName()))
                .isNotPresent();

        assertThat(i)
                .isOne();
    }
    @Test
    void isShouldFindAllProducts() {
        Product product = underTest.save(Product.builder()
                .amount(2)
                .name("Cikle")
                .price(1000F)
                .build());
        Product product1 = underTest.save(Product.builder()
                .amount(1)
                .name("Clock")
                .price(1500F)
                .build());
        Product product2 = underTest.save(Product.builder()
                .amount(2)
                .name("Sim")
                .price(1599F)
                .build());
        Product product3 = underTest.save(Product.builder()
                .amount(4)
                .name("lolipop")
                .price(999F)
                .build());

        assertThat(underTest.findAll())
                .hasSize(4)
                .contains(product, product1, product2, product3);
    }

    @Test
    void isShouldFindByIdProduct() {
        Product product1 = underTest.save(Product.builder()
                .amount(1)
                .name("Clock")
                .price(1500F)
                .build());
        Product product2 = underTest.save(Product.builder()
                .amount(2)
                .name("Sim")
                .price(1599F)
                .build());
        Product product3 = underTest.save(Product.builder()
                .amount(4)
                .name("lolipop")
                .price(999F)
                .build());

        assertThat(underTest.findById(product3.getId()))
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c).isEqualTo(product3).usingRecursiveComparison());
    }

    @Test
    void isShouldNotSaveProductWithNullName() {
        assertThatThrownBy(() -> underTest.save(Product.builder()
                .amount(1)
                .name(null)
                .price(1500F)
                .build()))
                .hasMessageContaining("")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void isShouldNotSaveProductWithNullPrice() {
        assertThatThrownBy(() -> underTest.save(Product.builder()
                .amount(1)
                .name("Clock")
                .price(null)
                .build()))
                .hasMessageContaining("")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}
