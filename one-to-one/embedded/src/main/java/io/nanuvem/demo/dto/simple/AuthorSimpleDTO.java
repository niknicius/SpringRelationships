package io.nanuvem.demo.dto.simple;

public class AuthorSimpleDTO {
  /**
   * id
   */
  private Long id;

  /**
   * Name.
   */
  private String name;
  /**
   * Genre.
   */
  private String genre;
  /**
   * Publisher.
   */
  private String publisher;

  /**
   * Gets id
   * @return id
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Sets id
   * @param id id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets name
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets name
   * @param name name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets genre
   * @return genre
   */
  public String getGenre() {
    return this.genre;
  }

  /**
   * Sets genre
   * @param genre genre
   */
  public void setGenre(String genre) {
    this.genre = genre;
  }

  /**
   * Gets publisher
   * @return publisher
   */
  public String getPublisher() {
    return this.publisher;
  }

  /**
   * Sets publisher
   * @param publisher publisher
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }
}
