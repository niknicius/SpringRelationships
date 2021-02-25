package io.nanuvem.onetoone.comboboxdemo.dto;

import io.nanuvem.onetoone.comboboxdemo.dto.simple.BookSimpleDTO;
import lombok.Data;

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
  private BookSimpleDTO book;

  public BookSimpleDTO getBook() {
    return book;
  }

  public void setBook(BookSimpleDTO book) {
    this.book = book;
  }
}
