package org.example.server_mobile.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.ApiResponse;
import org.example.server_mobile.dto.request.OrderRequest;
import org.example.server_mobile.dto.response.OrderResponse;
import org.example.server_mobile.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class OrderController implements IController<OrderRequest, OrderResponse> {
    OrderService orderService;


    @Override
    @PostMapping
    public ApiResponse<OrderResponse> create(@RequestBody OrderRequest request) {
        OrderResponse orderResponse = orderService.create(request);
        return ApiResponse.<OrderResponse>builder()
                .data(orderResponse)
                .build();

    }

    @Override
    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> findById(Long id) {
        OrderResponse orderResponse = orderService.findById(id);
        return ApiResponse.<OrderResponse>builder()
                .data(orderResponse)
                .build();

    }

    @Override
    @GetMapping
    public ApiResponse<List<OrderResponse>> findAll() {

        List<OrderResponse> categoryResponses = orderService.findAll();

        return ApiResponse.<List<OrderResponse>>builder()
                .code(200)
                .data(categoryResponses)
                .build();

    }

    @Override
    @PutMapping("/{id}")
    public ApiResponse<String> update(Long id, OrderRequest request) {

        request.setId(id);  // Set ID for update operation
        orderService.update(request);
        return ApiResponse.<String>builder()
                .data("Order updated successfully")
                .code(200)
                .build();

    }

    @Override
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(Long id) {
        orderService.delete(id);
        return ApiResponse.<String>builder()
                .data("Order deleted successfully")
                .code(200)
                .build();
    }

    public ApiResponse<List<OrderResponse>> findAllByUserId(Long userId) {
        List<OrderResponse> orderResponses = orderService.findAllByUserId(userId);
        return ApiResponse.<List<OrderResponse>>builder()
                .data(orderResponses)
                .build();
    }
    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrderResponse>> getByUserId(Long userId) {
        List<OrderResponse> orderResponses = orderService.getByUserId(userId);
        return ApiResponse.<List<OrderResponse>>builder()
                .data(orderResponses)
                .build();
    }

}
