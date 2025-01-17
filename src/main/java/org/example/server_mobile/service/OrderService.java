package org.example.server_mobile.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.server_mobile.dto.request.OrderRequest;
import org.example.server_mobile.dto.response.OrderResponse;
import org.example.server_mobile.entity.*;
import org.example.server_mobile.exception.ApiException;
import org.example.server_mobile.mapper.OrderMapper;
import org.example.server_mobile.repository.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService implements IService<OrderRequest, OrderResponse> {
    OrderRepo orderRepo;
    OrderMapper orderMapper;
    CartsItemRepo cartsItemRepo;
    CartsRepo cartsRepo;
    CartItemService cartItemService;
    DiscountRepo discountRepo;
    ProductRepo productRepo;


    @Override
    @PreAuthorize("hasRole('USER')")
    public OrderResponse create(OrderRequest orderRequest) {
        System.out.println("Received OrderRequest: " + orderRequest);

        // Chuyển đổi từ DTO sang entity và khởi tạo giá trị mặc định
        Order order = orderMapper.toOrder(orderRequest);
        order.setTotalDiscount(0.0);
        order.setTotalPrice(0.0);
        order.setGrandTotal(0.0);
        order.setShippingFee(orderRequest.getShippingFee());

        // Lưu đơn hàng ban đầu vào database
        order = orderRepo.save(order);

        // Lấy thông tin giỏ hàng
        Carts carts = cartsRepo.findById(orderRequest.getCartId())
                .orElseThrow(() -> new ApiException(400, "Cart not found with ID: " + orderRequest.getCartId()));

        // Tính tổng giá trị các mặt hàng trong giỏ
        double totalPrice = 0.0;
        double totalDiscount = 0.0;

        // Xử lý từng CartItem
        List<CartItem> cartItems = cartsItemRepo.findByCarts(carts);

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            // Kiểm tra tồn kho
            if (product.getStock() < cartItem.getQuantity()) {
                throw new ApiException(400, "Not enough stock for product: " + product.getName());
            }

            // Trừ số lượng tồn kho
            product.setStock(product.getStock() - cartItem.getQuantity());

            // Tính tổng giá và giảm giá
            totalPrice += cartItem.getQuantity() * product.getPrice();

            // Nếu có mã giảm giá, áp dụng
            if (orderRequest.getCode() != null) {
                Discount discount = discountRepo.findByCode(orderRequest.getCode());
                if (discount != null) {
                    totalDiscount += cartItem.getQuantity() * product.getPrice() * discount.getDiscountPercentage();
                }
            }

            // Cập nhật CartItem để gắn vào đơn hàng
            cartItem.setOrder(order);
            cartItem.setCarts(null); // Bỏ liên kết với giỏ hàng
        }

        // Tính tổng cộng
        double grandTotal = totalPrice - totalDiscount + orderRequest.getShippingFee();

        // Cập nhật thông tin vào đơn hàng
        order.setUser(carts.getUser());
        order.setTotalPrice(totalPrice);
        order.setTotalDiscount(totalDiscount);
        order.setGrandTotal(grandTotal);

        // Lưu các thay đổi
        cartsItemRepo.saveAll(cartItems); // Lưu các CartItem đã cập nhật
        orderRepo.save(order);            // Lưu đơn hàng
        productRepo.saveAll(cartItems.stream().map(CartItem::getProduct).toList()); // Lưu sản phẩm đã cập nhật

        // Map sang DTO phản hồi


        return orderMapper.toOrderResponse(order);
    }


    @Override
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public OrderResponse update(OrderRequest orderRequest) {

        Order order = orderMapper.toOrder(orderRequest);
        Order savedOrder = orderRepo.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }


    @Override
    public OrderResponse findById(Long id) {

        return orderRepo.findById(id).map(orderMapper::toOrderResponse).orElse(null);

    }

    @Override
    public void delete(Long id) {

        orderRepo.deleteById(id);

    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> findAll() {

        return orderRepo.findAll().stream().map(orderMapper::toOrderResponse).toList();
    }

    public List<OrderResponse> findAllByUserId(Long userId) {
        return orderRepo.findByUserId(userId).stream().map(orderMapper::toOrderResponse).toList();
    }

    @PreAuthorize("hasRole('USER')")
    public List<OrderResponse> getByUserId(Long userId) {
        return orderRepo.findByUserId(userId).stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }
}
