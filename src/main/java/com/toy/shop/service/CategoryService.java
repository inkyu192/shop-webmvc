package com.toy.shop.service;

import com.toy.shop.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryDto.Response saveCategory(CategoryDto.Save requestDto);

    Page<CategoryDto.Response> categories(Pageable pageable);

    CategoryDto.Response category(Long id);

    CategoryDto.Response updateCategory(Long id, CategoryDto.Update requestDto);

    void deleteCategory(Long id);
}
