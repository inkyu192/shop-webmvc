package spring.web.java.domain.order.repository;

import static spring.web.java.domain.member.QMember.*;
import static spring.web.java.domain.order.QDelivery.*;
import static spring.web.java.domain.order.QOrder.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import spring.web.java.domain.order.Delivery;
import spring.web.java.domain.order.Order;

public class OrderCustomRepositoryImpl implements OrderCustomRepository {

	private final EntityManager entityManager;
	private final JPAQueryFactory queryFactory;

	public OrderCustomRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Page<Order> findAllUsingJpql(
		Pageable pageable, Long memberId, Order.Status orderStatus, Delivery.Status deliveryStatus
	) {
		String countJpql = """
			SELECT COUNT(o)
			FROM Order o
			JOIN o.member m
			JOIN o.delivery d
			WHERE 1 = 1
			""";

		String contentJpql = """
			SELECT o
			FROM Order o
			JOIN FETCH o.member m
			JOIN FETCH o.delivery d
			WHERE 1 = 1
			""";

		if (memberId != null) {
			countJpql += " AND m.id = :memberId";
			contentJpql += " AND m.id = :memberId";
		}

		if (orderStatus != null) {
			countJpql += " AND o.status = :orderStatus";
			contentJpql += " AND o.status = :orderStatus";
		}

		if (deliveryStatus != null) {
			countJpql += " AND d.status = :deliveryStatus";
			contentJpql += " AND d.status = :deliveryStatus";
		}

		TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
		TypedQuery<Order> contentQuery = entityManager.createQuery(contentJpql, Order.class)
			.setFirstResult((int)pageable.getOffset())
			.setMaxResults(pageable.getPageSize());

		if (memberId != null) {
			countQuery.setParameter("memberId", memberId);
			contentQuery.setParameter("memberId", memberId);
		}

		if (orderStatus != null) {
			countQuery.setParameter("orderStatus", orderStatus);
			contentQuery.setParameter("orderStatus", orderStatus);
		}

		if (deliveryStatus != null) {
			countQuery.setParameter("deliveryStatus", deliveryStatus);
			contentQuery.setParameter("deliveryStatus", deliveryStatus);
		}

		return new PageImpl<>(contentQuery.getResultList(), pageable, countQuery.getSingleResult());
	}

	@Override
	public Page<Order> findAllUsingQueryDsl(
		Pageable pageable, Long memberId, Order.Status orderStatus, Delivery.Status deliveryStatus
	) {
		int count = queryFactory
			.selectOne()
			.from(order)
			.join(order.member, member)
			.join(order.delivery, delivery)
			.where(
				memberId != null ? member.id.eq(memberId) : null,
				orderStatus != null ? order.status.eq(orderStatus) : null,
				deliveryStatus != null ? delivery.status.eq(deliveryStatus) : null
			)
			.fetch()
			.size();

		List<Order> content = queryFactory
			.select(order)
			.from(order)
			.join(order.member, member)
			.fetchJoin()
			.join(order.delivery, delivery)
			.fetchJoin()
			.where(
				memberId != null ? member.id.eq(memberId) : null,
				orderStatus != null ? order.status.eq(orderStatus) : null,
				deliveryStatus != null ? delivery.status.eq(deliveryStatus) : null
			)
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			.fetch();

		return new PageImpl<>(content, pageable, count);
	}
}
