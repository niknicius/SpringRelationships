package io.nanuvem.manytomany.associate.demo.core.dto;

public class SortDTO {
  private String columns;
  private String order = "ASC";

  public String getColumns() {
    return columns;
  }

  public void setColumns(String columns) {
    this.columns = columns;
  }

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }
}
