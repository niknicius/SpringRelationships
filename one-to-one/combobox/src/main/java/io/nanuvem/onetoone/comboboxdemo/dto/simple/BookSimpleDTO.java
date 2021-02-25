package io.nanuvem.onetoone.comboboxdemo.dto.simple;

import lombok.Data;

@Data
public class BookSimpleDTO {
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
