package io.nanuvem.onetomany.masterdetails.demo.dto.simple;

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
