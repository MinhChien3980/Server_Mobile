package org.example.server_mobile.repository;

import org.example.server_mobile.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepo extends JpaRepository<Discount, Long> {
    Discount findByCode(String code);
}
