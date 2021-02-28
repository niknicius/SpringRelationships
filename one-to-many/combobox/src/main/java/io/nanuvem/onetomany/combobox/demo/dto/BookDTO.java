package io.nanuvem.onetomany.combobox.demo.dto;

import io.nanuvem.onetomany.combobox.demo.dto.simple.AuthorSimpleDTO;
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

}
