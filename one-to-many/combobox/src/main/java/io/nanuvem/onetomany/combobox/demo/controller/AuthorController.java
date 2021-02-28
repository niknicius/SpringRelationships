package io.nanuvem.onetomany.combobox.demo.controller;

import io.nanuvem.onetomany.combobox.demo.core.dto.PageDTO;
import io.nanuvem.onetomany.combobox.demo.core.dto.SearchDTO;
import io.nanuvem.onetomany.combobox.demo.dto.AuthorDTO;
import io.nanuvem.onetomany.combobox.demo.exception.BusinessException;
import io.nanuvem.onetomany.combobox.demo.model.Author;
import io.nanuvem.onetomany.combobox.demo.service.AuthorService;
import io.nanuvem.onetomany.combobox.demo.util.JsonUtil;
import io.nanuvem.onetomany.combobox.demo.util.MappingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("authors")
public class AuthorController {
  private final AuthorService service;

  public AuthorController(AuthorService service) {
    this.service = service;
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id)
    throws BusinessException {
    this.service.delete(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<AuthorDTO> update(
    @PathVariable Long id,
    @RequestBody AuthorDTO newModelDTO
  )
    throws BusinessException {
    Author newModel = MappingUtil.mapTo(newModelDTO, Author.class);
    return ResponseEntity.ok(
      MappingUtil.mapTo(service.update(newModel, id), AuthorDTO.class)
    );
  }

  @PatchMapping(path = "/{id}")
  public ResponseEntity<AuthorDTO> patch(
    @PathVariable Long id,
    @RequestBody AuthorDTO newModelDTO
  )
    throws BusinessException {
    Author newModel = MappingUtil.mapTo(newModelDTO, Author.class);
    return ResponseEntity.ok(
      MappingUtil.mapTo(service.patch(newModel, id), AuthorDTO.class)
    );
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<AuthorDTO> getOne(@PathVariable("id") Long id)
    throws BusinessException {
    return ResponseEntity.ok(
      MappingUtil.mapTo(service.getOne(id), AuthorDTO.class)
    );
  }

  @PostMapping
  public ResponseEntity<AuthorDTO> insert(@RequestBody AuthorDTO modelDTO)
    throws BusinessException {
    Author model = service.insert(MappingUtil.mapTo(modelDTO, Author.class));
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(MappingUtil.mapTo(model, AuthorDTO.class));
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
      AuthorDTO.class
    );
    return ResponseEntity.ok(page);
  }
}
