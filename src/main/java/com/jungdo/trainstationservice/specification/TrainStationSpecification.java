package com.jungdo.trainstationservice.specification;

import com.jungdo.trainstationservice.form.TrainStationFilterForm;
import com.jungdo.trainstationservice.entity.TrainStation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class TrainStationSpecification {

    public static Specification<TrainStation> buildWhere(TrainStationFilterForm filterForm){
        Specification<TrainStation> where = null;

        if(filterForm == null)
            return where;

        if(!StringUtils.isEmpty(filterForm.getSearch())){
            String search = filterForm.getSearch();
            search = search.trim();
            CustomSpecification nameSpecification = new CustomSpecification("name", search);
            where = Specification.where(nameSpecification);
        }

        if(!StringUtils.isEmpty(filterForm.getProperty())){
            CustomSpecification propertySpecification = new CustomSpecification("property", filterForm.getProperty());
            if(where == null){
                where = propertySpecification;
            }else {
                where = where.and(propertySpecification);
            }
        }

        return where;
    }

    @RequiredArgsConstructor
    static class CustomSpecification implements Specification<TrainStation>{
        @NonNull
        private String field;

        @NonNull
        private Object value;

        @Override
        public Predicate toPredicate(Root<TrainStation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

            if(field.equalsIgnoreCase("name")){
                return criteriaBuilder.like(root.get("nameStation"), "%" + value + "%");
            }

            if(field.equalsIgnoreCase("property")){
                return criteriaBuilder.equal(root.get("propertiesOfStation"), value);
            }

            return null;
        }
    }
}
