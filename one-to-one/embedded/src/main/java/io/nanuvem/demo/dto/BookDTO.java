package io.nanuvem.demo.dto;

import io.nanuvem.demo.dto.simple.AuthorSimpleDTO;
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
