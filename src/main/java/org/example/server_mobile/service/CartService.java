package org.example.server_mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.AddressesRequest;
import org.example.server_mobile.dto.response.CartsResponse;
import org.example.server_mobile.entity.*;
import org.example.server_mobile.exception.ApiException;
import org.example.server_mobile.mapper.AddressesMapper;
import org.example.server_mobile.mapper.CartItemMapper;
import org.example.server_mobile.mapper.CartsMapper;
import org.example.server_mobile.repository.CartsRepo;
import org.example.server_mobile.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartsRepo cartsRepo;
    CartsMapper cartsMapper;
    CartItemMapper cartItemMapper;
    AddressesMapper addressesMapper;
    ProductRepo productRepo;

    public CartsResponse getCart(Long id) {
        Carts carts = cartsRepo.findById(id).orElseThrow(
                () -> new ApiException(404, "Cart not found"));
        var cartResponse = cartsMapper.toCartResponse(carts);
        validation(carts, cartResponse);
        return cartResponse;
    }

    public CartsResponse findByUserId(Long userId) {
        Carts carts = cartsRepo.findByUserId(userId).orElseThrow(
                () -> new ApiException(404, "Cart not found"));
        var cartResponse = cartsMapper.toCartResponse(carts);
        validation(carts, cartResponse);
        return cartResponse;
    }

    public CartsResponse createCart(Long userId) {
        Carts carts = Carts.builder().user(User.builder().id(userId).build()).cartItems(new ArrayList<>())
                .build();
        cartsRepo.save(carts);

        return cartsMapper.toCartResponse(carts);
    }

    private void validation(Carts carts, CartsResponse cartsResponse) {
        String cartErrorMessage = null;
        var cartItems = carts.getCartItems();

        // check quantity of product in cart
        {
            var carItemsResponse = cartItems.stream()
                    .map(cartItemMapper::toCartItemResponse)
                    .map(cartItemResponse -> {
                        String itemCartLineMessage;
                        var product = cartItemResponse.getProduct();
                        var quantity = cartItemResponse.getQuantity();
                        var stock = product.getStock();
                        if (stock < quantity) {
                            itemCartLineMessage = "Not enough stock for product " + product.getName();
                            cartsResponse.setErrorMessage(itemCartLineMessage);
                        }

                        return cartItemResponse;

                    })
                    .collect(Collectors.toList());
            int errorCartItem = (int) carItemsResponse.stream()
                    .filter(cartItemResponse -> cartItemResponse.getErrorMessage() != null).count();
            if (errorCartItem > 0 && cartErrorMessage == null) {
                cartErrorMessage = "Some products are not available";
                cartsResponse.setErrorMessage(cartErrorMessage);
            }
            cartsResponse.setCartItems(carItemsResponse);
        }

    }

    public void addToCart(Long cartId, Long productId, Integer quantity) {
        Carts carts = cartsRepo.findById(cartId).orElseThrow(
                () -> new ApiException(404, "Cart not found"));
        var cartItems = carts.getCartItems();
        var product = productRepo.findById(productId).orElseThrow(
                () -> new ApiException(404, "Product variant not found"));
        var cartItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (product.getStock() < quantity) {
            throw new ApiException(400, "Not enough stock for product");
        }
        if (cartItem == null) {
            cartItem = CartItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .carts(carts)
                    .build();
            cartItems.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        carts.setCartItems(cartItems);
        cartsRepo.save(carts);
    }

    public void updateCart(Long cartId, Long productId, Integer quantity) {
        Carts carts = cartsRepo.findById(cartId).orElseThrow(
                () -> new ApiException(404, "Cart not found"));
        var cartItems = carts.getCartItems();
        var cartItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
        }
        carts.setCartItems(cartItems);
        cartsRepo.save(carts);
    }

    public void removeFromCart(Long cartId, Long productId) {
        Carts carts = cartsRepo.findById(cartId).orElseThrow(
                () -> new ApiException(404, "Cart not found"));
        var cartItems = carts.getCartItems();
        var cartItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (cartItem != null) {
            cartItems.remove(cartItem);
        }
        carts.setCartItems(cartItems);
        cartsRepo.save(carts);
    }

    public void setAddress(AddressesRequest addressesRequest, Long cartId) {
        Addresses addresses = addressesMapper.toAddress(addressesRequest);
        Carts carts = cartsRepo.findById(cartId).orElseThrow(
                () -> new ApiException(404, "Cart not found"));
        carts.setAddresses(addresses);
        cartsRepo.save(carts);
    }

    public void checkUserPermissionCart(Long userId, Long cartId) {
        Carts carts = cartsRepo.findById(cartId).orElseThrow(
                () -> new ApiException(404, "Cart not found"));
        if (!carts.getUser().getId().equals(userId)) {
            throw new ApiException(403, "Forbidden");
        }
    }
}
