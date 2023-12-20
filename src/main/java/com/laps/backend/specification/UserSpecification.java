package com.laps.backend.specification;

import com.laps.backend.models.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> byKeywords(List<String> fields, String[] keywords) {
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