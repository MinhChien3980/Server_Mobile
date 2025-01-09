package org.example.server_mobile.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.ApiResponse;
import org.example.server_mobile.dto.request.CategoryRequest;
import org.example.server_mobile.dto.response.CategoryResponse;
import org.example.server_mobile.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class CategoryController implements IController<CategoryRequest, CategoryResponse> {

    CategoryService categoryService;

    @Override
    @PostMapping("/create")
    public ApiResponse<CategoryResponse> create(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.create(categoryRequest);
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryResponse)
                .build();
    }

    @Override
    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> findById(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.findById(id);
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryResponse)
                .build();
    }

    @Override
    @PostMapping("/{id}/update")
    public ApiResponse<String> update(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        categoryRequest.setId(id);  // Set ID for update operation
        categoryService.update(categoryRequest);
        return ApiResponse.<String>builder()
                .data("Category updated successfully")
                .code(200)
                .build();
    }

    @Override
    @PostMapping("/{id}/delete")
    public ApiResponse<String> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResponse.<String>builder()
                .data("Category deleted successfully")
                .code(200)
                .build();
    }

    @Override
    @GetMapping
    public ApiResponse<List<CategoryResponse>> findAll() {
        List<CategoryResponse> categories = categoryService.findAll();
        return ApiResponse.<List<CategoryResponse>>builder()
                .code(200)
                .data(categories)
                .build();
    }
}
