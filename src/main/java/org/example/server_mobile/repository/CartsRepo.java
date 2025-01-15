package org.example.server_mobile.repository;

import org.example.server_mobile.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartsRepo extends JpaRepository<Carts, Long> {
    Optional<Carts> findByUserId(Long userId);
}
