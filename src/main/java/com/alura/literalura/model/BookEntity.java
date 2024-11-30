package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Marcamos como entidad de base de datos
@Entity
// Le damos un nombre a la tabla
@Table(name = "books")
public class BookEntity {

  // Decimos que es un id y que se debe generar autoincremental
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String title;
  private Long downloadCount;
  private String language;

  // Enlazamos la relacion uno a muchos entre Book y Author
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private AuthorEntity author;

  // Constructor necesario para poder usar JPA
  public BookEntity() {
  }

  public BookEntity(BooksRecord booksRecord) {
    this.title = booksRecord.results().getFirst().title();
    this.downloadCount = booksRecord.results().getFirst().downloadCount();
    this.language = booksRecord.results().getFirst().languages().getFirst();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public Long getDownloadCount() {
    return downloadCount;
  }

  public void setDownloadCount(Long downloadCount) {
    this.downloadCount = downloadCount;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public AuthorEntity getAuthor() {
    return author;
  }

  public void setAuthor(AuthorEntity author) {
    this.author = author;
  }

  @Override
  public String toString() {
    return "title='" + title + '\'' +
      ", downloadCount=" + downloadCount +
      ", authors=" + author +
      ", languages=" + language;
  }
}
