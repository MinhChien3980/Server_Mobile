package org.example.server_mobile.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.ApiResponse;
import org.example.server_mobile.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class TestController {

    CategoryService categoryService;


    @GetMapping
    public ApiResponse<String> getAllCategories() {
        return ApiResponse.<String>builder()
                .code(200)
                .data("Thành công")
                .build();
    }
}
