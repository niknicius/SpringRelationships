package io.nanuvem.onetoone.comboboxdemo.core.dto;

import lombok.Data;

@Data
public class SearchDTO {
  private SortDTO sort = new SortDTO();
  private String search = "";
  private Integer pageSize = 10;
  private Integer currentPage = 0;
}
