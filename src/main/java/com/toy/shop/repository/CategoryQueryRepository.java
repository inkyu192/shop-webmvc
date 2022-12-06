package com.toy.shop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.shop.domain.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.toy.shop.domain.QCategory.category;


//@Repository
public class CategoryQueryRepository implements CategoryCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final JPAQueryFactory queryFactory;

    public CategoryQueryRepository() {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Category> findAll(String searchWord) {
        return queryFactory.select(category)
                .from(category)
                .where(likeSearchWord(searchWord))
                .fetch();
    }

    private BooleanExpression likeSearchWord(String searchWord) {
        if (StringUtils.hasText(searchWord)) {
            return category.name.like("%" + searchWord + "%");
        }

        return null;
    }
}
