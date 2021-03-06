package io.nanuvem.manytomany.associate.demo.controller;

import io.nanuvem.manytomany.associate.demo.core.dto.PageDTO;
import io.nanuvem.manytomany.associate.demo.core.dto.SearchDTO;
import io.nanuvem.manytomany.associate.demo.dto.AuthorBookDTO;
import io.nanuvem.manytomany.associate.demo.exception.BusinessException;
import io.nanuvem.manytomany.associate.demo.model.AuthorBook;
import io.nanuvem.manytomany.associate.demo.service.AuthorBookService;
import io.nanuvem.manytomany.associate.demo.util.JsonUtil;
import io.nanuvem.manytomany.associate.demo.util.MappingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("authors-books")
public class AuthorBookController {

    private final AuthorBookService service;

    public AuthorBookController(AuthorBookService service) {
        this.service = service;
    }

    @PostMapping(value = "multiple")
    public ResponseEntity<List<AuthorBook>> insertMultiple(@RequestBody List<AuthorBookDTO> modelsDTO)
            throws BusinessException {
        List<AuthorBook> insertedModels = service.insertMultiple(MappingUtil.mapToList(modelsDTO, AuthorBook.class));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MappingUtil.mapToList(insertedModels, AuthorBookDTO.class));
    }

    @PostMapping
    public ResponseEntity<AuthorBookDTO> insert(@RequestBody AuthorBookDTO authorBookDTO) throws BusinessException {
        AuthorBook model = service.insert(MappingUtil.mapTo(authorBookDTO, AuthorBook.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(MappingUtil.mapTo(model, AuthorBookDTO.class));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorBookDTO> update(@PathVariable Long id, @RequestBody AuthorBookDTO newAuthorBookDTO) throws BusinessException {
        AuthorBook newAuthorBook = MappingUtil.mapTo(newAuthorBookDTO, AuthorBook.class);
        return ResponseEntity.ok(MappingUtil.mapTo(service.update(newAuthorBook, id), AuthorBookDTO.class));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<AuthorBookDTO> patch(@PathVariable Long id, @RequestBody AuthorBookDTO newAuthorBookDTO) throws BusinessException {
        AuthorBook newAuthorBook = MappingUtil.mapTo(newAuthorBookDTO, AuthorBook.class);
        return ResponseEntity.ok(MappingUtil.mapTo(service.patch(newAuthorBook, id), AuthorBookDTO.class));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorBookDTO> getOne(@PathVariable("id") Long id) throws BusinessException {
        return ResponseEntity.ok(MappingUtil.mapTo(service.getOne(id), AuthorBookDTO.class));
    }

    @GetMapping
    public ResponseEntity<PageDTO> listAll(HttpServletRequest request, @PathParam("filters") String filters) throws BusinessException {
        SearchDTO searchDTO = new SearchDTO();
        if (filters != null) {
            searchDTO = JsonUtil.fromJson(filters, SearchDTO.class);
        }
        PageDTO page = MappingUtil.mapPageItems(service.findPaginated(searchDTO), AuthorBookDTO.class);
        return ResponseEntity.ok(page);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws BusinessException {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
}
