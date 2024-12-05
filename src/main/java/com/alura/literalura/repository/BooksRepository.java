package com.alura.literalura.repository;

import com.alura.literalura.model.AuthorEntity;
import com.alura.literalura.model.Authors;
import com.alura.literalura.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BooksRepository extends JpaRepository<BookEntity, Long> {
  @Query("SELECT b FROM BookEntity b WHERE b.title ILIKE %:bookName%")
  List<BookEntity> searchByTitle(String bookName);

  @Query("SELECT a FROM AuthorEntity a")
  List<AuthorEntity> searchAllAuthors();

  @Query("SELECT a FROM AuthorEntity a WHERE a.birthYear = :birthDate AND a.deathYear = :deathDate")
  List<AuthorEntity> searAuthorsByYear(Integer birthDate, Integer deathDate);

  List<BookEntity> findByLanguage(String language);

  @Query("SELECT a FROM AuthorEntity a WHERE a.birthYear <= :date AND a.deathYear >= :date")
  List<AuthorEntity> searchAliveAuthorsByYear(int date);
}
