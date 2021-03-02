package io.nanuvem.manytomany.combobox.demo.core.repository;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchSpecification<M> implements Specification<M> {
  private String search = "";

  public SearchSpecification(String search) {
    this.search = search;
  }

  public String getSearch() {
    return search;
  }

  public void setSearch(String search) {
    this.search = search;
  }

  @Override
  public Predicate toPredicate(
    Root<M> root,
    CriteriaQuery<?> criteriaQuery,
    CriteriaBuilder criteriaBuilder
  ) {
    List<Predicate> predicates = new ArrayList<>();
    if (search != null) {
      predicates.add(buildSimpleSearchPredicate(root, criteriaBuilder));
    }
    return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
  }

  private Predicate buildSimpleSearchPredicate(
    Root<M> root,
    CriteriaBuilder criteriaBuilder
  ) {
    List<Predicate> predicates = new ArrayList<>();
    List<Field> fieldsToBeSearched = getValidFields(root);

    fieldsToBeSearched.forEach(
      attr ->
        predicates.add(
          criteriaBuilder.like(
            criteriaBuilder.lower(root.get(attr.getName()).as(String.class)),
            likeValue(search.toLowerCase())
          )
        )
    );
    return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
  }

  private List<Field> getValidFields(Root<M> root) {
    List<String> permittedTypes = Arrays.asList(
      "string",
      "date",
      "integer",
      "long",
      "boolean",
      "instant"
    );
    List<Field> fields = new ArrayList<>();
    for (Field field : root.getJavaType().getDeclaredFields()) {
      if (
        permittedTypes.contains(field.getType().getSimpleName().toLowerCase())
      ) {
        fields.add(field);
      }
    }
    return fields;
  }

  private String likeValue(String search) {
    return String.format("%%%s%%", search);
  }
}
