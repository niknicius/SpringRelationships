package io.nanuvem.onetoone.comboboxdemo.core.dto;

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
