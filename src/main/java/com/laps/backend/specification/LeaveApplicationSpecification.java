package com.laps.backend.specification;

import com.laps.backend.models.LeaveApplication;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class LeaveApplicationSpecification {

    public static Specification<LeaveApplication> byKeywords(List<String> fields, String[] keywords) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String field : fields) {
                for (String keyword : keywords) {
                    predicates.add(cb.like(cb.lower(root.get(field)), "%" + keyword.toLowerCase() + "%"));
                }
            }
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }
}
