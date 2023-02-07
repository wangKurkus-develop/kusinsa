package com.kurkus.kusinsa.repository.point;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kurkus.kusinsa.entity.QPoint.*;
import static com.kurkus.kusinsa.entity.QUser.*;

import com.kurkus.kusinsa.dto.request.point.PointSearchCondition;
import com.kurkus.kusinsa.entity.Point;
import com.kurkus.kusinsa.enums.PointType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public class PointRepositoryImpl implements PointRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    public PointRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Point> searchPageCondition(PointSearchCondition condition, Pageable pageable) {
        List<Long> ids = queryFactory
                .select(point.id)
                .from(point)
                .where(
                        point.user.id.eq(condition.getUserId())
                )
                .orderBy(point.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        List<Point> result = queryFactory
                .select(point)
                .from(point)
                .join(point.user, user)
                .where(
                        point.id.in(ids),
                        divisionEq(condition.getDivision()),
                        point.deleted.eq(false)

                )
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(point.count())
                .from(point)
                .where(
                        point.deleted.eq(false),
                        point.user.id.eq(condition.getUserId())
                        , divisionEq(condition.getDivision())
                );

        return new PageImpl<>(result, pageable, countQuery.fetchOne());
    }


    private BooleanExpression divisionEq(PointType pointType) {
        if (pointType == PointType.ALL || pointType == null) {
            return null;
        }
        return point.division.eq(pointType);
    }

}
