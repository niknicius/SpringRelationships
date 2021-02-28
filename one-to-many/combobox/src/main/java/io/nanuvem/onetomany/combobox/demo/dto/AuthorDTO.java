package io.nanuvem.onetomany.combobox.demo.dto;

import io.nanuvem.onetomany.combobox.demo.dto.simple.BookSimpleDTO;
import io.nanuvem.onetomany.combobox.demo.model.Book;
import lombok.Data;

import java.util.List;

@Data
public class AuthorDTO {
  /**
   * id
   */
  private Long id;

  /**
   * Name.
   */
  private String name;
  /**
   * Genre.
   */
  private String genre;
  /**
   * Publisher.
   */
  private String publisher;
  /**
   * Books.
   */
  private List<BookSimpleDTO> books;

}
