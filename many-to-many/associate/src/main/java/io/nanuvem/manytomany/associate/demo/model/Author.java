package io.nanuvem.manytomany.associate.demo.model;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

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
  @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH })
  @JoinTable(
          name = "author_book",
          joinColumns = { @JoinColumn(name = "author_id") },
          inverseJoinColumns = { @JoinColumn(name = "book_id") }
  )
  private List<Book> books;

  @Column(name = "deleted", nullable = false)
  private Boolean deleted = false;
  
}
