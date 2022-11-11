package com.toy.shop.service;

import com.toy.shop.domain.Category;
import com.toy.shop.dto.CategoryResponseDto;
import com.toy.shop.dto.CategorySaveRequestDto;
import com.toy.shop.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void save() {
        CategorySaveRequestDto requestDto = new CategorySaveRequestDto();
        requestDto.setName("category1");

        Long saveId = categoryService.save(requestDto).getId();

        Category findCategory = categoryRepository.findById(saveId).get();

        assertThat(saveId).isEqualTo(findCategory.getId());
    }

    @Test
    void findById() {
        CategorySaveRequestDto requestDto = new CategorySaveRequestDto();
        requestDto.setName("category1");

        Category saveCategory = Category.createCategory(requestDto);
        categoryRepository.save(saveCategory);

        entityManager.flush();
        entityManager.clear();

        CategoryResponseDto responseDto = categoryService.findById(saveCategory.getId());

        Assertions.assertThat(saveCategory.getId()).isEqualTo(responseDto.getId());
    }
}