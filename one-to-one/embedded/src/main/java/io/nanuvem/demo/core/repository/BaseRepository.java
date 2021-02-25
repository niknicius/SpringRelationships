package io.nanuvem.demo.core.repository;

import io.nanuvem.demo.core.dto.SearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<M, T>
  extends PagingAndSortingRepository<M, T>, JpaSpecificationExecutor<M> {
  default Page<M> findAll(SearchDTO searchFilter, Pageable pageable) {
    return this.findAll(
        generateSpecification(searchFilter.getSearch()),
        pageable
      );
  }

  default Specification<M> generateSpecification(String search) {
    return new SearchSpecification<>(search);
  }
}
