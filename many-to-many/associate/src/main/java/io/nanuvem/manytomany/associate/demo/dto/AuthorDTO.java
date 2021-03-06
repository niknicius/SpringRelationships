package io.nanuvem.manytomany.associate.demo.dto;

import io.nanuvem.manytomany.associate.demo.dto.simple.BookSimpleDTO;
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
