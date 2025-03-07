package org.example.server_mobile.repository;

import java.util.List;
import java.util.Optional;

import org.example.server_mobile.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<List<Product>> findAllByIdIn(List<Long> ids);
    @Query("SELECT p FROM Product p WHERE p.isDeleted = true")
    List<Product> findAllDeletedProducts();
}
