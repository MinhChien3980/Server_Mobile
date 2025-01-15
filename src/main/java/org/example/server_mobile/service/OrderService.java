package org.example.server_mobile.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.server_mobile.dto.request.OrderRequest;
import org.example.server_mobile.dto.response.OrderResponse;
import org.example.server_mobile.entity.Order;
import org.example.server_mobile.mapper.OrderMapper;
import org.example.server_mobile.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService implements IService<OrderRequest, OrderResponse> {
    OrderRepo orderRepo;
    OrderMapper orderMapper;
    CartItemService cartItemService;


    @Override
    public OrderResponse create(OrderRequest orderRequest) {
        Order order = orderMapper.toOrder(orderRequest);
        order = orderRepo.save(order);

        double totalPrice = cartItemService.findAllByCarts(orderRequest.getCartId()).stream()
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice())
                .sum();


        double grandTotal = totalPrice - order.getTotalDiscount();
        order.setTotalPrice(totalPrice);
        order.setGrandTotal(grandTotal);
        orderRepo.save(order);

        cartItemService.changeCartToOrder(orderRequest.getCartId(), order.getId());

        return orderMapper.toOrderResponse(order);
    }


    @Override
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
    public List<OrderResponse> findAll() {

        return orderRepo.findAll().stream().map(orderMapper::toOrderResponse).toList();
    }

    public List<OrderResponse> findAllByUserId(Long userId) {
        return orderRepo.findByUserId(userId).stream().map(orderMapper::toOrderResponse).toList();
    }
}
