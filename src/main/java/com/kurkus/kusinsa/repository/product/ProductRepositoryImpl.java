package com.kurkus.kusinsa.repository.product;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.kurkus.kusinsa.entity.QBrand.*;
import static com.kurkus.kusinsa.entity.QCategory.*;
import static com.kurkus.kusinsa.entity.QProduct.*;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.kurkus.kusinsa.dto.request.product.ProductSearchCondition;
import com.kurkus.kusinsa.entity.*;
import com.kurkus.kusinsa.utils.QueryDslUtil;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;


public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Product> searchPageCondition(ProductSearchCondition condition, Pageable pageable) {
        List<OrderSpecifier> ORDERS = getAllOrderSpecifiers(pageable);

        List<Product> result = queryFactory.select(product)
                .from(product)
                .join(product.brand, brand).fetchJoin()
                .join(product.category, category).fetchJoin()
                .where(
                        product.deleted.eq(false),
                        brandEq(condition.getBrandId()),
                        categoryEq(condition.getCategoryId())
                )
                .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count())
                .from(product)
                .join(product.brand, brand)
                .join(product.category, category)
                .where(
                        product.deleted.eq(false),
                        brandEq(condition.getBrandId()),
                        categoryEq(condition.getCategoryId())
                );

        return new PageImpl<>(result, pageable,  countQuery.fetchOne());
    }

    private BooleanExpression categoryEq(Long categoryId) {
        return categoryId == null ? null : product.category.id.eq(categoryId);
    }

    private BooleanExpression brandEq(Long brandId) {
        return brandId == null ? null : product.brand.id.eq(brandId);
    }

    private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {

        List<OrderSpecifier> ORDERS = new ArrayList<>();

        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "product_name":
                        OrderSpecifier<?> orderName = QueryDslUtil.getSortedColumn(direction, product.name, "name");
                        ORDERS.add(orderName);
                        break;
                    case "price":
                        OrderSpecifier<?> orderPrice = QueryDslUtil.getSortedColumn(direction, product.price, "price");
                        ORDERS.add(orderPrice);
                        break;
                    case "category_name":
                        OrderSpecifier<?> orderCategoryName = QueryDslUtil.getSortedColumn(direction, product.category.name, "name");
                        ORDERS.add(orderCategoryName);
                        break;
                    case "brand_name":
                        OrderSpecifier<?> orderBrandName = QueryDslUtil.getSortedColumn(direction, product.brand.name, "name");
                        ORDERS.add(orderBrandName);
                        break;
                    case "created_at":
                        OrderSpecifier<?> orderCreatedAt = QueryDslUtil.getSortedColumn(direction, product.createdAt, "createdAt");
                        ORDERS.add(orderCreatedAt);
                        break;
                    case "likes":
                        OrderSpecifier<?> orderLikes = QueryDslUtil.getSortedColumn(direction, product.likes, "likes");
                        ORDERS.add(orderLikes);
                        break;
                    default:
                        break;
                }
            }
        }

        return ORDERS;
    }
}
