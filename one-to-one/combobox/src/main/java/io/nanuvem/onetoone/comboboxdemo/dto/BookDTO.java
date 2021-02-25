package io.nanuvem.onetoone.comboboxdemo.dto;

import io.nanuvem.onetoone.comboboxdemo.dto.simple.AuthorSimpleDTO;
import lombok.Data;

@Data
public class BookDTO {
  /**
   * id
   */
  private Long id;

  /**
   * Title.
   */
  private String title;
  /**
   * Edition.
   */
  private Double edition;
  /**
   * Authors.
   */
  private AuthorSimpleDTO author;
}
