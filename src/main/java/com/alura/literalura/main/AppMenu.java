package com.alura.literalura.main;

import com.alura.literalura.model.AuthorEntity;
import com.alura.literalura.model.BookEntity;
import com.alura.literalura.model.BooksRecord;
import com.alura.literalura.model.Results;
import com.alura.literalura.repository.BooksRepository;
import com.alura.literalura.services.ApiConnection;
import com.alura.literalura.services.DataConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AppMenu {

  private final String API_URL = "https://gutendex.com/books/";
  private final Scanner scan = new Scanner(System.in);
  private final ApiConnection api = new ApiConnection();
  private final DataConvert converter = new DataConvert();
  private final BooksRepository repository;

  public AppMenu(BooksRepository repository) {
    this.repository = repository;
  }


  public void showMenu() {
    var option = -1;

    while (option != 0) {

      System.out.println("""
                
        Ingrese la opcion que desea realizar:
        1. Buscar libro por nombre
        2. Listar todos los libros buscados
                
        0. Salir
        """);

      try {

        option = Integer.parseInt(scan.nextLine());

        switch (option) {
          case 1:
            searchBookByName();
            break;
          case 2:
            showAllBooks();
            break;
          case 0:
            System.out.println("*** Programa terminado ***");
            break;
          default:
            System.out.println("Opcion inválida, intente nuevamente");
        }
      } catch (NumberFormatException e) {
        System.out.println("Ingrese unicamente números");
      }
    }
  }

  private void showAllBooks() {
    List<BookEntity> books = repository.findAll();
    books.forEach(b -> System.out.printf("""
      *******************************
      Libro: %s
        - Descargas: %d
        - Idioma: %s
        - Autor:
          - Nombre: %s
          - Año de nacimiento: %d
          - Año de muerte: %d
      """, b.getTitle(), b.getDownloadCount(), b.getLanguage(), b.getAuthor().getName(), b.getAuthor().getBirthYear(), b.getAuthor().getDeathYear()));
  }

  private void searchBookByName() {
    // Solicitar el nombre del libro
    System.out.println("Ingrese el nombre del libro que desea buscar");
    String bookName = scan.nextLine();
    try {

      // Ir a la api y obtener el libro
      var json = api.getApiData(this.API_URL + "?search=" + bookName.replace(" ", "+"));

      // Convertir el resultado en objeto Java
      BooksRecord response = converter.convertFromJson(json, BooksRecord.class);

      // TODO hacer que en caso de que la repuesta venga vacia, envie un mensaje al respecto

      // Obtener el primer resultado de la respuesta
      Results firstResult = response.results().getFirst();

      // guardar el libro y su autor en la base de datos
      BookEntity bookEntity = new BookEntity(response);
      String authorName = firstResult.authors().getFirst().name();
      Integer authorBirth = firstResult.authors().getFirst().birthYear();
      Integer authorDeat = firstResult.authors().getFirst().deathYear();
      AuthorEntity author = new AuthorEntity(authorName, authorBirth, authorDeat);
      bookEntity.setAuthor(author);
      repository.save(bookEntity);

      //imprimir la informacion necesaria
      System.out.println("Titulo: " + firstResult.title());
      System.out.println("Número de descargas: " + firstResult.downloadCount());
      System.out.println("Autores:");
      firstResult.authors().forEach(a -> System.out.printf("""
        - Nombre: %s
        - Fecha de nacimiento: %d
        - Fecha de muerte: %d %n""", a.name(), a.birthYear(), a.deathYear()));
      System.out.println("Idiomas:");
      firstResult.languages().forEach(e -> System.out.println("- " + e));

    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    } catch (DataIntegrityViolationException e) {
      System.out.println("Ya existe el libro en la base de datos");
    }

  }
}
