package com.shop.ecommerse.repository.product;

import com.shop.ecommerse.domain.entity.Product;
import com.shop.ecommerse.domain.entity.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @Query("select p from Product p")
    List<Product> findAll();

    @Modifying
    @Transactional
    @Query("DELETE FROM Product P WHERE P.id=:id")
    int delete(@Param("id") Long id);
    @Query("SELECT P FROM Product P WHERE P.name=:name")
    Optional<Product> findByName(@Param("name") String name);
    @Query("SELECT P FROM Product P WHERE P.price BETWEEN ?1 AND ?2")
    List<Product> findProductsBetweenPrice(Integer bottomPrice, Integer roofPrice);

    Optional<Product> findByUrl(String url);

    List<Product> findAllByProductCategory(Pageable pageable, ProductCategory productCategory);

    List<Product> findTop8ByOrderByDateCreatedDesc();

    List<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Product> findTop8ByProductCategoryAndIdIsNot(ProductCategory productCategory, Long id);

    List<Product> findAllByProductCategoryIsNot(ProductCategory productCategory, Pageable pageable);
}
