package com.alura.literalura.model;

import jakarta.persistence.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class AuthorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;
  private Integer birthYear;
  private Integer deathYear;

  // Indicamos que hace parte de la relacion uno a uno
  @OneToOne(mappedBy = "author")
  private BookEntity book;

  public AuthorEntity(){}

  public AuthorEntity(String name, Integer birthYear, Integer deathYear) {
    this.name = name;
    this.birthYear = birthYear;
    this.deathYear = deathYear;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getBirthYear() {
    return birthYear;
  }

  public void setBirthYear(Integer birthYear) {
    this.birthYear = birthYear;
  }

  public Integer getDeathYear() {
    return deathYear;
  }

  public void setDeathYear(Integer deathYear) {
    this.deathYear = deathYear;
  }

  public BookEntity getBooks() {
    return book;
  }

  public void setBooks(BookEntity books) {
    this.book = books;
  }

  @Override
  public String toString() {
    return "name='" + name + '\'' +
      ", birthYear=" + birthYear +
      ", deathYear=" + deathYear +
      ", book=" + book;
  }
}
