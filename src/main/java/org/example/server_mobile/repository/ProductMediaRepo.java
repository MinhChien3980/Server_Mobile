package org.example.server_mobile.repository;

import org.example.server_mobile.entity.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMediaRepo extends JpaRepository<ProductMedia, Long> {
    void deleteByProductId(Long id);
}
