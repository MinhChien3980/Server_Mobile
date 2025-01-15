package org.example.server_mobile.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.server_mobile.dto.request.OrderRequest;
import org.example.server_mobile.dto.response.OrderResponse;
import org.example.server_mobile.entity.Carts;
import org.example.server_mobile.entity.Discount;
import org.example.server_mobile.entity.Order;
import org.example.server_mobile.exception.ApiException;
import org.example.server_mobile.mapper.OrderMapper;
import org.example.server_mobile.repository.CartsItemRepo;
import org.example.server_mobile.repository.CartsRepo;
import org.example.server_mobile.repository.DiscountRepo;
import org.example.server_mobile.repository.OrderRepo;
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


    @Override
    @PreAuthorize("hasRole('USER')")
    public OrderResponse create(OrderRequest orderRequest) {
        System.out.println("Received OrderRequest: " + orderRequest);
        // Log request đầu vào

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
        double totalPrice = cartsItemRepo.findByCarts(carts).stream()
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice()) // Giả định: `getPrice` trả giá tiền mỗi sản phẩm
                .sum();


        // Tính giảm giá
        Discount discount = discountRepo.findByCode(orderRequest.getCode());


        double totalDiscount = (discount != null) ? discount.getDiscountPercentage() * totalPrice : 0.0;

        // Tính tổng cộng
        double grandTotal = totalPrice - totalDiscount + orderRequest.getShippingFee();

        // Cập nhật thông tin vào đơn hàng
        order.setUser(carts.getUser());
        order.setTotalPrice(totalPrice);
        order.setTotalDiscount(totalDiscount);
        order.setGrandTotal(grandTotal);


        // Lưu đơn hàng đã cập nhật
        orderRepo.save(order);

        // Chuyển đổi giỏ hàng thành đơn hàng
        cartItemService.changeCartToOrder(orderRequest.getCartId(), order.getId());

        OrderResponse orderResponse = orderMapper.toOrderResponse(order);
        orderResponse.setUserId(carts.getUser().getId());
        orderResponse.setUserName(carts.getUser().getFullName());
        orderResponse.setCreatedAt(order.getCreatedAt());


        // Trả về phản hồi
        return orderResponse;
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
