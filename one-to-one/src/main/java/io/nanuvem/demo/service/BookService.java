package io.nanuvem.demo.service;

import io.nanuvem.demo.core.dto.PageDTO;
import io.nanuvem.demo.core.dto.SearchDTO;
import io.nanuvem.demo.exception.BusinessException;
import io.nanuvem.demo.model.Book;
import io.nanuvem.demo.repository.BookRepository;
import io.nanuvem.demo.util.BeanUtils;
import io.nanuvem.demo.util.TranslatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {
  @Autowired
  private BookRepository repository;

  @Autowired
  private AuthorService authorService;

  @Transactional(readOnly = true)
  public Book getOne(Long id) throws BusinessException {
    return repository
      .findById(id)
      .orElseThrow(() -> new BusinessException(TranslatorUtil.ITEM_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public List<Book> findAll() {
    return StreamSupport
      .stream(repository.findAll().spliterator(), false)
      .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public PageDTO findPaginated(SearchDTO searchDTO) {
    Pageable pageable = createPageRequest(searchDTO);
    Page<Book> jpaPage = repository.findAll(searchDTO, pageable);
    return pageDTOFromJPAPage(jpaPage);
  }

  @Transactional
  public void delete(Long id) throws BusinessException {
    Book model = repository
      .findById(id)
      .orElseThrow(() -> new BusinessException(TranslatorUtil.ITEM_NOT_FOUND));
    repository.delete(model);
  }

  @Transactional
  public void delete(Long[] ids) throws BusinessException {
    for (Long id : ids) {
      this.delete(id);
    }
  }

  private void validateInsert(Book model) throws BusinessException {
    // SonarLint
  }

  @Transactional
  public void preInsert(Book model) throws BusinessException {
  }

  @Transactional
  public Book insert(Book model) throws BusinessException {
    this.validateInsert(model);
    preInsert(model);
    return repository.save(model);
  }

  private void validateUpdate(Book model, Long id) throws BusinessException {
    this.repository.findById(id)
        .orElseThrow(
          () -> new BusinessException(TranslatorUtil.ITEM_NOT_FOUND)
        );
  }

  @Transactional
  public Book update(Book newModel, Long id) throws BusinessException {
    this.validateUpdate(newModel, id);
    return update(newModel, id, false);
  }

  @Transactional
  public Book patch(Book newModel, Long id) throws BusinessException {
    this.validateUpdate(newModel, id);
    return update(newModel, id, true);
  }

  private Book update(Book newModel, Long id, boolean isPatch)
    throws BusinessException {
    Book dbModel = repository
      .findById(id)
      .orElseThrow(() -> new BusinessException(TranslatorUtil.ITEM_NOT_FOUND));


    if (!dbModel.getId().equals(newModel.getId())) {
      throw new BusinessException(TranslatorUtil.ITEM_UPDATE_INCONSISTENT);
    }
    if (isPatch) {
      BeanUtils.copyNonNullProperties(dbModel, newModel);
    } else {
      BeanUtils.copyAllProperties(newModel, dbModel);
    }

    return repository.save(dbModel);
  }

  private PageDTO pageDTOFromJPAPage(Page<Book> jpaPage) {
    PageDTO page = new PageDTO();
    page.setItems(jpaPage.getContent());
    page.setCurrentPage(jpaPage.getPageable().getPageNumber());
    page.setPageSize(jpaPage.getPageable().getPageSize());
    page.setTotalPages(jpaPage.getTotalPages());
    page.setTotalRecords(jpaPage.getTotalElements());
    return page;
  }

  private Pageable createPageRequest(SearchDTO searchDTO) {
    if (searchDTO.getSort().getColumns() == null) {
      return PageRequest.of(
        searchDTO.getCurrentPage(),
        searchDTO.getPageSize()
      );
    }
    String[] columns = searchDTO
      .getSort()
      .getColumns()
      .replace(" ", "")
      .split(",");
    return PageRequest.of(
      searchDTO.getCurrentPage(),
      searchDTO.getPageSize(),
      Sort.Direction.fromString(searchDTO.getSort().getOrder()),
      columns
    );
  }
}
