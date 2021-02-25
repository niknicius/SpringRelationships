package io.nanuvem.onetoone.comboboxdemo.model;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity(name = "book")
@Data
@SQLDelete(sql = "UPDATE book SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * title
   */
  @Column(name = "title")
  private String title;

  /**
   * edition
   */
  @Column(name = "edition")
  private Double edition;

  @Column(name = "deleted", nullable = false)
  private Boolean deleted = false;
}
