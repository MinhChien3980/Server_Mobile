package org.example.server_mobile.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.response.CategoryResponse;
import org.example.server_mobile.entity.Category;
import org.example.server_mobile.mapper.CategoryMapper;
import org.example.server_mobile.repository.CategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CatgoryService {
    private static final Logger log = LoggerFactory.getLogger(Category.class);
    CategoryRepo categoryRepo;
    CategoryMapper categoryMapper;

    public CategoryResponse createCategory(Category category) {
//        log.info("Creating category: {}", category);
        return categoryMapper.toCategoryResponse(categoryRepo.save(category));
    }

    public CategoryResponse updateCategory(Category category) {
//        log.info("Updating category: {}", category);
        return categoryMapper.toCategoryResponse(categoryRepo.save(category));
    }

    public void deleteCategory(Long id) {
//        log.info("Deleting category with id: {}", id);
        categoryRepo.deleteById(id);
    }

    public CategoryResponse getCategory(Long id) {
//        log.info("Getting category with id: {}", id);
        return categoryMapper.toCategoryResponse(categoryRepo.findById(id).orElse(null));
    }

    public List<CategoryResponse> getAllCategories() {
//        log.info("Getting all categories");
        return categoryRepo.findAll().stream().map(categoryMapper::toCategoryResponse).collect(Collectors.toList());
    }

    public static void main(String[] args) {

    }

}
