package com.cma.gcvms.repository.support;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Helper to create list of {@link Order} out of {@link OrderBy}s.
 */
@Named
@Singleton
public class OrderByUtil {

    @Inject
    private JpaUtil jpaUtil;

    public <E> List<Order> buildJpaOrders(Iterable<OrderBy> orders, Root<E> root, CriteriaBuilder builder, SearchParameters sp) {
        List<Order> jpaOrders = newArrayList();
        for (OrderBy ob : orders) {
            Path<?> path = jpaUtil.getPath(root, ob.getAttributes());
            jpaOrders.add(ob.isOrderDesc() ? builder.desc(path) : builder.asc(path));
        }
        return jpaOrders;
    }
}