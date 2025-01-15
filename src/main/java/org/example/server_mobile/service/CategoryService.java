package org.example.server_mobile.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.CategoryRequest;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService implements IService<CategoryRequest, CategoryResponse> {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    CategoryRepo categoryRepo;
    CategoryMapper categoryMapper;

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        //        log.info("Creating category: {}", category);
        Category category = categoryMapper.toCategory(categoryRequest);
        return categoryMapper.toCategoryResponse(categoryRepo.save(category));
    }

    @Override
    public CategoryResponse update(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toCategory(categoryRequest);
        return categoryMapper.toCategoryResponse(categoryRepo.save(category));
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryMapper.toCategoryResponse(categoryRepo.findById(id).orElse(null));
    }

    @Override
    public void delete(Long id) {
        categoryRepo.deleteById(id);
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepo.findAll().stream().map(categoryMapper::toCategoryResponse).collect(Collectors.toList());
    }
}
