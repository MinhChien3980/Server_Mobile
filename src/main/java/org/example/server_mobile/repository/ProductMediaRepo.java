package org.example.server_mobile.repository;

import org.example.server_mobile.entity.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductMediaRepo extends JpaRepository<ProductMedia, String> {
    void deleteByProductId(Long id);
}