package com.jungdo.trainstationservice.specification;

import com.jungdo.trainstationservice.form.TechnicalStatusFilterForm;
import com.jungdo.trainstationservice.entity.TechnicalStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TechnicalStatusSpecification {

    public static Specification<TechnicalStatus> buildWhere(TechnicalStatusFilterForm filterForm) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(filterForm.getSearch())) {
                String search = filterForm.getSearch();
                search = search.trim();
                predicates.add(builder.like(root.get("trainStation").get("nameStation"), "%" + search + "%"));
            }

            if (!StringUtils.isEmpty(filterForm.getState())) {
                predicates.add(builder.equal(root.get("state"), filterForm.getState()));
            }

            if (!StringUtils.isEmpty(filterForm.getSort())) {
                query.orderBy(builder.desc(root.get(filterForm.getSort())));
            } else {
                query.orderBy(builder.desc(root.get("updatedAt")));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
