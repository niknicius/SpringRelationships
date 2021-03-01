package io.nanuvem.onetomany.masterdetails.demo.dto;

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
