package io.nanuvem.manytomany.associate.demo.service;

import io.nanuvem.manytomany.associate.demo.core.dto.PageDTO;
import io.nanuvem.manytomany.associate.demo.core.dto.SearchDTO;
import io.nanuvem.manytomany.associate.demo.exception.BusinessException;
import io.nanuvem.manytomany.associate.demo.model.AuthorBook;
import io.nanuvem.manytomany.associate.demo.repository.AuthorBookRepository;
import io.nanuvem.manytomany.associate.demo.util.BeanUtils;
import io.nanuvem.manytomany.associate.demo.util.TranslatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorBookService {

    @Autowired
    private AuthorBookRepository repository;

    @Transactional(readOnly = true)
    public AuthorBook getOne(Long id) throws BusinessException {
        return repository
                .findById(id)
                .orElseThrow(() -> new BusinessException(TranslatorUtil.ITEM_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<AuthorBook> findAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PageDTO findPaginated(SearchDTO searchDTO) {
        Pageable pageable = createPageRequest(searchDTO);
        Page<AuthorBook> jpaPage = repository.findAll(searchDTO, pageable);
        return pageDTOFromJPAPage(jpaPage);
    }

    @Transactional
    public void delete(Long id) throws BusinessException {
        AuthorBook model = repository
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

    private void validateInsert(AuthorBook model) throws BusinessException {
        // SonarLint
    }

    @Transactional
    public void preInsert(AuthorBook model) throws BusinessException {

    }

    @Transactional
    public AuthorBook insert(AuthorBook model) throws BusinessException {
        this.validateInsert(model);
        preInsert(model);
        return repository.save(model);
    }

    @Transactional
    public List<AuthorBook> insertMultiple(List<AuthorBook> models) throws BusinessException {
        List<AuthorBook> insertedModels = new ArrayList<>();
        for (AuthorBook model: models) {
            AuthorBook ab = this.repository.findAuthorBookByAuthorAndBook(model.getAuthor().getId(), model.getBook().getId());
            if (ab == null) {
                insertedModels.add(this.insert(model));
            }
        }
        return insertedModels;
    }

    private void validateUpdate(AuthorBook model, Long id) throws BusinessException {
        AuthorBook dbModel =
                this.repository.findById(id)
                        .orElseThrow(
                                () -> new BusinessException(TranslatorUtil.ITEM_NOT_FOUND)
                        );
    }

    @Transactional
    public AuthorBook update(AuthorBook newModel, Long id) throws BusinessException {
        this.validateUpdate(newModel, id);
        return update(newModel, id, false);
    }

    @Transactional
    public AuthorBook patch(AuthorBook newModel, Long id) throws BusinessException {
        this.validateUpdate(newModel, id);
        return update(newModel, id, true);
    }

    private AuthorBook update(AuthorBook newModel, Long id, boolean isPatch)
            throws BusinessException {
        AuthorBook dbModel = repository
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

    private PageDTO pageDTOFromJPAPage(Page<AuthorBook> jpaPage) {
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
