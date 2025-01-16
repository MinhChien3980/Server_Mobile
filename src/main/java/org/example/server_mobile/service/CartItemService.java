package org.example.server_mobile.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.CartsItemRequest;
import org.example.server_mobile.dto.response.CartItemResponse;
import org.example.server_mobile.entity.CartItem;
import org.example.server_mobile.entity.Carts;
import org.example.server_mobile.entity.Order;
import org.example.server_mobile.entity.Product;
import org.example.server_mobile.entity.enums.TypeItem;
import org.example.server_mobile.mapper.CartsItemMapper;
import org.example.server_mobile.repository.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemService implements IService<CartsItemRequest, CartItemResponse> {
    CartsItemMapper cartItemMapper;
    CartsItemRepo cartsItemRepo;
    OrderRepo orderRepo;
    CartsRepo cartsRepo;
    ProductRepo productRepo;
    DiscountRepo discountRepo;

    @Override
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public CartItemResponse create(CartsItemRequest request) {
        CartItem cartItem = cartItemMapper.toCartItem(request);

        Optional<Product> product = productRepo.findById(request.getProductId());
        Optional<Carts> cart = cartsRepo.findById(request.getCartId());


        List<CartItem> cartItemList = cartsItemRepo.findByCarts(cart.get());
        double grandTotal = cartItemList.stream()
                .mapToDouble(c -> c.getProduct().getPrice())
                .sum();

        cartItem.setGrandTotal(grandTotal);
        cartItem.setCarts(cart.get());
        cartItem.setProduct(product.get());
        cartsItemRepo.save(cartItem);
        return cartItemMapper.toCartItemResponse(cartItem);

    }

    @Override
    public CartItemResponse update(CartsItemRequest request) {

        CartItem cartItem = cartItemMapper.toCartItem(request);
        cartsItemRepo.save(cartItem);
        return cartItemMapper.toCartItemResponse(cartItem);

    }

    @Override
    public CartItemResponse findById(Long id) {

        return cartItemMapper.toCartItemResponse(cartsItemRepo.findById(id).get());

    }

    @Override
    public void delete(Long id) {
        cartsItemRepo.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<CartItemResponse> findAll() {
        return cartsItemRepo.findAll().stream().map(cartItemMapper::toCartItemResponse).toList();

    }

    public List<CartItemResponse> findAllByCarts(Long cartId) {
        Carts cart = cartsRepo.findById(cartId).get();
        List<CartItem> findAllByCarts = cartsItemRepo.findByCartsId(cartId);
//        return cartsItemRepo.findByCarts(
//                        cart).stream()
//                .filter(c -> c.getTypeItem().equals(TypeItem.CART))
//                .map(cartItemMapper::toCartItemResponse)
//                .toList();
        return findAllByCarts.stream().filter(c -> c.getTypeItem().equals(TypeItem.CART)).map(cartItemMapper::toCartItemResponse).toList();
    }

    public List<CartItemResponse> findAllByOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).get();
        return cartsItemRepo.findByOrder(order).stream()
                .filter(c -> c.getTypeItem().equals(TypeItem.ORDER))
                .map(cartItemMapper::toCartItemResponse)
                .toList();
    }

    public void changeCartToOrder(Long cartId, Long orderId) {
        Carts carts = cartsRepo.findById(cartId).get();
        Order order = orderRepo.findById(orderId).get();
        List<CartItem> cartItems = cartsItemRepo.findByCarts(carts);
        cartItems.forEach(c -> {
                    c.setOrder(order);
                    c.setTypeItem(TypeItem.ORDER);
                }
        );
        System.out.println("Chuyển đổi thành công");
        cartsItemRepo.saveAll(cartItems);
    }


}
