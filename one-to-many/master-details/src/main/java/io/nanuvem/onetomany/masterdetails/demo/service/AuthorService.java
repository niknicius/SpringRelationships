package io.nanuvem.onetomany.masterdetails.demo.service;


import io.nanuvem.onetomany.masterdetails.demo.core.dto.PageDTO;
import io.nanuvem.onetomany.masterdetails.demo.core.dto.SearchDTO;
import io.nanuvem.onetomany.masterdetails.demo.exception.BusinessException;
import io.nanuvem.onetomany.masterdetails.demo.model.Author;
import io.nanuvem.onetomany.masterdetails.demo.model.Book;
import io.nanuvem.onetomany.masterdetails.demo.repository.AuthorRepository;
import io.nanuvem.onetomany.masterdetails.demo.util.BeanUtils;
import io.nanuvem.onetomany.masterdetails.demo.util.TranslatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorService {
  @Autowired
  private AuthorRepository repository;

  @Autowired
  private BookService bookService;

  @Transactional(readOnly = true)
  public Author getOne(Long id) throws BusinessException {
    return repository
      .findById(id)
      .orElseThrow(() -> new BusinessException(TranslatorUtil.ITEM_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public List<Author> findAll() {
    return StreamSupport
      .stream(repository.findAll().spliterator(), false)
      .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public PageDTO findPaginated(SearchDTO searchDTO) {
    Pageable pageable = createPageRequest(searchDTO);
    Page<Author> jpaPage = repository.findAll(searchDTO, pageable);
    return pageDTOFromJPAPage(jpaPage);
  }

  @Transactional
  public void delete(Long id) throws BusinessException {
    Author model = repository
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

  private void validateInsert(Author model) {
    // SonarLint
  }

  @Transactional
  public void preInsert(Author model) throws BusinessException {
    for(Book b: model.getBooks()){
      if(b.getId() == null){
        this.bookService.insert(b);
      }
    }
  }

  @Transactional
  public Author insert(Author model) throws BusinessException {
    this.validateInsert(model);
    preInsert(model);
    return repository.save(model);
  }

  private void validateUpdate(Author model, Long id) throws BusinessException {
      this.repository.findById(id)
        .orElseThrow(
          () -> new BusinessException(TranslatorUtil.ITEM_NOT_FOUND)
        );
  }

  @Transactional
  public void preUpdate(Author model){
    // empty
  }

  @Transactional
  public Author update(Author newModel, Long id) throws BusinessException {
    this.validateUpdate(newModel, id);
    preUpdate(newModel);
    return update(newModel, id, false);
  }

  @Transactional
  public Author patch(Author newModel, Long id) throws BusinessException {
    this.validateUpdate(newModel, id);
    preUpdate(newModel);
    return update(newModel, id, true);
  }

  private Author update(Author newModel, Long id, boolean isPatch)
          throws BusinessException {
    Author dbModel = repository
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

  private PageDTO pageDTOFromJPAPage(Page<Author> jpaPage) {
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
