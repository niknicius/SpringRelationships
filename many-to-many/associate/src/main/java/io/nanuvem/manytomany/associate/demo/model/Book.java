package io.nanuvem.manytomany.associate.demo.model;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

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

  @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH })
  @JoinTable(
          name = "author_book",
          joinColumns = { @JoinColumn(name = "book_id") },
          inverseJoinColumns = { @JoinColumn(name = "author_id") }
  )
  private List<Author> authors;

  @Column(name = "deleted", nullable = false)
  private Boolean deleted = false;
}
