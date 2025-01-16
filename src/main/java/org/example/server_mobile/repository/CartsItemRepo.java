package org.example.server_mobile.repository;

import org.example.server_mobile.entity.CartItem;
import org.example.server_mobile.entity.Carts;
import org.example.server_mobile.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartsItemRepo extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCarts(Carts carts);

    List<CartItem> findByCarts_Id(Long cartsId);

    List<CartItem> findByOrder(Order order);

    List<CartItem> findByCartsId(Long cartsId);

    CartItem findByCarts_IdAndProductId(Long cartsId, Long productId);
}
