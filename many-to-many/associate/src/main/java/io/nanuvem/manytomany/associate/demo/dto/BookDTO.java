package io.nanuvem.manytomany.associate.demo.dto;

import io.nanuvem.manytomany.associate.demo.dto.simple.AuthorSimpleDTO;
import lombok.Data;

import java.util.List;

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

  private List<AuthorSimpleDTO> authors;

}
