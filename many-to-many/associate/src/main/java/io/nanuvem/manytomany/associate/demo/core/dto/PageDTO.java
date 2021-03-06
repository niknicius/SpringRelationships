package io.nanuvem.manytomany.associate.demo.core.dto;

import java.util.List;

public class PageDTO {
  private List<?> items;
  private Integer pageSize;
  private Integer currentPage;
  private Long totalRecords;
  private Integer totalPages;

  public List<?> getItems() {
    return items;
  }

  public void setItems(List<?> items) {
    this.items = items;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public Long getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(Long totalRecords) {
    this.totalRecords = totalRecords;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }
}
