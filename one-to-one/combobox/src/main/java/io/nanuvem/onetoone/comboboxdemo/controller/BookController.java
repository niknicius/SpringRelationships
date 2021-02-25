package io.nanuvem.onetoone.comboboxdemo.controller;

import io.nanuvem.onetoone.comboboxdemo.core.dto.PageDTO;
import io.nanuvem.onetoone.comboboxdemo.core.dto.SearchDTO;
import io.nanuvem.onetoone.comboboxdemo.dto.BookDTO;
import io.nanuvem.onetoone.comboboxdemo.exception.BusinessException;
import io.nanuvem.onetoone.comboboxdemo.model.Book;
import io.nanuvem.onetoone.comboboxdemo.service.BookService;
import io.nanuvem.onetoone.comboboxdemo.util.JsonUtil;
import io.nanuvem.onetoone.comboboxdemo.util.MappingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("books")
public class BookController {
  private final BookService service;

  public BookController(BookService service) {
    this.service = service;
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id)
    throws BusinessException {
    this.service.delete(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<BookDTO> update(
    @PathVariable Long id,
    @RequestBody BookDTO newModelDTO
  )
          throws BusinessException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Book newModel = MappingUtil.mapTo(newModelDTO, Book.class);
    return ResponseEntity.ok(
      MappingUtil.mapTo(service.update(newModel, id), BookDTO.class)
    );
  }

  @PatchMapping(path = "/{id}")
  public ResponseEntity<BookDTO> patch(
    @PathVariable Long id,
    @RequestBody BookDTO newModelDTO
  )
          throws BusinessException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Book newModel = MappingUtil.mapTo(newModelDTO, Book.class);
    return ResponseEntity.ok(
      MappingUtil.mapTo(service.patch(newModel, id), BookDTO.class)
    );
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<BookDTO> getOne(@PathVariable("id") Long id)
    throws BusinessException {
    return ResponseEntity.ok(
      MappingUtil.mapTo(service.getOne(id), BookDTO.class)
    );
  }

  @PostMapping
  public ResponseEntity<BookDTO> insert(@RequestBody BookDTO modelDTO)
    throws BusinessException {
    Book model = service.insert(MappingUtil.mapTo(modelDTO, Book.class));
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(MappingUtil.mapTo(model, BookDTO.class));
  }

  @GetMapping
  public ResponseEntity<PageDTO> listAll(
    HttpServletRequest request,
    @PathParam("filters") String filters
  )
    throws BusinessException {
    SearchDTO searchDTO = new SearchDTO();
    if (filters != null) {
      searchDTO = JsonUtil.fromJson(filters, SearchDTO.class);
    }
    PageDTO page = MappingUtil.mapPageItems(
      service.findPaginated(searchDTO),
      BookDTO.class
    );
    return ResponseEntity.ok(page);
  }
}
