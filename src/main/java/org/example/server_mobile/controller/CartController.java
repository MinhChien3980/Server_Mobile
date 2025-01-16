package org.example.server_mobile.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.*;
import org.example.server_mobile.dto.response.CartItemResponse;
import org.example.server_mobile.dto.response.CartsResponse;
import org.example.server_mobile.entity.CartItem;
import org.example.server_mobile.entity.enums.TypeItem;
import org.example.server_mobile.mapper.CartsItemMapper;
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
    private final CartsItemMapper cartsItemMapper;

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
//    @GetMapping("/user/{id}")
//    ApiResponse<CartsResponse> getCartByUserId(@PathVariable Long id) {
//        return ApiResponse.<CartsResponse>builder()
//                .data(cartService.findByUserId(id))
//                .build();
//    }

    @PostMapping("/additem")
    public ApiResponse<String> addToCart(@RequestBody CartsItemRequest cartsItemRequest) {
        // Xác định loại sản phẩm là "CART"
        cartsItemRequest.setTypeItem(TypeItem.CART);

        // Kiểm tra sản phẩm đã tồn tại trong giỏ hàng hay chưa
        CartItem existingCartItem = cartsItemRepo.findByCarts_IdAndProductId(
                cartsItemRequest.getCartId(),
                cartsItemRequest.getProductId()
        );

        if (existingCartItem != null) {
            // Nếu sản phẩm đã tồn tại, cập nhật số lượng
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartsItemRequest.getQuantity());
            cartsItemRepo.save(existingCartItem); // Lưu thay đổi vào cơ sở dữ liệu

            return ApiResponse.<String>builder()
                    .data("Product quantity updated in cart")
                    .code(200)
                    .build();
        } else {
            // Nếu sản phẩm chưa tồn tại, thêm mới
            cartItemService.create(cartsItemRequest);

            return ApiResponse.<String>builder()
                    .data("Product added to cart")
                    .code(200)
                    .build();
        }
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
