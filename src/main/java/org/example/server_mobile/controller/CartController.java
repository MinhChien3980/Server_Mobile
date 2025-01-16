package org.example.server_mobile.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.*;
import org.example.server_mobile.dto.response.CartItemResponse;
import org.example.server_mobile.dto.response.CartsResponse;
import org.example.server_mobile.entity.enums.TypeItem;
import org.example.server_mobile.repository.CartsItemRepo;
import org.example.server_mobile.repository.CartsRepo;
import org.example.server_mobile.service.CartItemService;
import org.example.server_mobile.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CartController {
    CartsItemRepo cartsItemRepo;
    CartService cartService;
    CartItemService cartItemService;
    CartsRepo cartsRepo;

    @PostMapping("/create")
    ApiResponse<CartsResponse> createCart(@RequestParam Long userId) {
        return ApiResponse.<CartsResponse>builder()
                .data(cartService.createCart(userId))
                .build();
    }

    @GetMapping
    ApiResponse<CartsResponse> getCart(@RequestParam Long id) {
        return ApiResponse.<CartsResponse>builder()
                .data(cartService.getCart(id))
                .build();
    }
    @GetMapping("/user/{id}")
    ApiResponse<CartsResponse> getCartByUserId(@PathVariable Long id) {
        return ApiResponse.<CartsResponse>builder()
                .data(cartService.findByUserId(id))
                .build();
    }

    @PostMapping("/additem")
    public ApiResponse<String> addToCart(@RequestBody CartsItemRequest cartsItemRequest) {
        cartsItemRequest.setTypeItem(TypeItem.CART);
        cartItemService.create(cartsItemRequest);
        return ApiResponse.<String>builder().data("Product added to cart").code(200).build();
    }
    @PostMapping("/{id}/addToCart")
    public ApiResponse<String> addToCart(@RequestParam Long cartId, @RequestBody AddToCardRequest addToCardRequest) {
        cartService.addToCart(cartId, addToCardRequest.getProductId(),
                addToCardRequest.getQuantity());
        return ApiResponse.<String>builder().data("Product added to cart").code(200).build();
    }
    @PostMapping("/{id}/update")
    public ApiResponse<String> updateCart(@RequestParam Long cartId,
            @RequestBody UpdateCartQuanlityRequest addToCardRequest) {
        cartService.updateCart(cartId, addToCardRequest.getProductId(),
                addToCardRequest.getQuantity());
        return ApiResponse.<String>builder().data("Product updated in cart").code(200).build();
    }

    @GetMapping("/user")
    public ApiResponse<CartsResponse> getCartByUserId(@RequestParam("userId") Long userId) {
        CartsResponse item = cartService.findByUserId(userId);

        List<CartItemResponse> cartItemResponseList = item.getCartItems();
        cartItemResponseList.forEach(cartItemResponse -> {
            System.out.println("Price " + cartItemResponse.getProduct().getPrice());
            System.out.println("Quantity " + cartItemResponse.getQuantity());
        });
        double grandTotal = cartItemResponseList.stream().mapToDouble(itemCart ->
            itemCart.getProduct().getPrice() * itemCart.getQuantity()



        ).sum();
        System.out.println("grandTotal: " + grandTotal);
        item.setGrandTotal(grandTotal);
        return ApiResponse.<CartsResponse>builder()
                .code(200)
                .data(item)
                .build();
    }

    @PostMapping("/{id}/delete")
    public ApiResponse<String> deleteFromCart(@RequestParam Long cartId,
            @RequestBody DeleteCartItemRequest addToCardRequest) {
        cartService.removeFromCart(cartId, addToCardRequest.getProductId());
        return ApiResponse.<String>builder().data("Product deleted from cart").code(200).build();
    }

    @PostMapping("/{id}/setAddress")
    public ApiResponse<String> setAddress(@RequestParam Long cartId, @RequestParam AddressesRequest address) {
        cartService.setAddress(address, cartId);
        return ApiResponse.<String>builder().data("Address set").code(200).build();
    }
}
