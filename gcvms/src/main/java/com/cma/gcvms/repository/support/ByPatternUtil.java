package com.cma.gcvms.repository.support;

import static com.google.common.collect.Lists.newArrayList;
import static javax.persistence.metamodel.Attribute.PersistentAttributeType.MANY_TO_ONE;
import static javax.persistence.metamodel.Attribute.PersistentAttributeType.ONE_TO_ONE;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;

@Named
@Singleton
public class ByPatternUtil {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private JpaUtil jpaUtil;

    /**
     * Lookup entities having at least one String attribute matching the passed sp's pattern
     */
    @SuppressWarnings("unchecked")
    public <T> Predicate byPattern(Root<T> root, CriteriaBuilder builder, SearchParameters sp, Class<T> type) {
        if (!sp.hasSearchPattern()) {
            return null;
        }

        List<Predicate> predicates = newArrayList();
        EntityType<T> entity = em.getMetamodel().entity(type);
        String pattern = sp.getSearchPattern();

        for (SingularAttribute<? super T, ?> attr : entity.getSingularAttributes()) {
            if (attr.getPersistentAttributeType() == MANY_TO_ONE || attr.getPersistentAttributeType() == ONE_TO_ONE) {
                continue;
            }

            if (attr.getJavaType() == String.class) {
                predicates.add(jpaUtil.stringPredicate((Expression<String>) root.get(jpaUtil.attribute(entity, attr)), pattern, sp, builder));
            }
        }

        return jpaUtil.orPredicate(builder, predicates);
    }
}