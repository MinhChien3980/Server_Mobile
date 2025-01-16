package org.example.server_mobile.repository;

import org.example.server_mobile.dto.response.ProductMediaResponse;
import org.example.server_mobile.entity.Product;
import org.example.server_mobile.entity.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMediaRepo extends JpaRepository<ProductMedia, Long> {
    void deleteByProductId(Long id);

    List<ProductMedia> findByProduct_Id(Long productId);
}
