package io.nanuvem.onetoone.comboboxdemo.model;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity(name = "author")
@Data
@SQLDelete(sql = "UPDATE author SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * name
   */
  @Column(name = "name")
  private String name;

  /**
   * genre
   */
  @Column(name = "genre")
  private String genre;

  /**
   * publisher
   */
  @Column(name = "publisher")
  private String publisher;

  /**
   * books
   */
  @OneToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "book_id", referencedColumnName = "id", unique = true)
  private Book book;

  @Column(name = "deleted", nullable = false)
  private Boolean deleted = false;

}
