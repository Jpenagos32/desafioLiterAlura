package com.alura.literalura.repository;

import com.alura.literalura.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BooksRepository extends JpaRepository<BookEntity, Long> {
  @Query("SELECT b FROM BookEntity b WHERE b.title ILIKE %:bookName%")
  List<BookEntity> searchByTitle(String bookName);

}
