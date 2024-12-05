package com.alura.literalura.main;

import com.alura.literalura.model.AuthorEntity;
import com.alura.literalura.model.BookEntity;
import com.alura.literalura.model.BooksRecord;
import com.alura.literalura.model.Results;
import com.alura.literalura.repository.BooksRepository;
import com.alura.literalura.services.ApiConnection;
import com.alura.literalura.services.DataConvert;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.IOException;
import java.util.ArrayList;
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
        3. Listar autores de los libros buscados
        4. Listar autores vivos por rango de años
        5. Listar libros por idioma
        6. Listar autores vivos en determinado año
                
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
          case 3:
            listAllAuthors();
            break;
          case 4:
            listAuthorsByYears();
            break;
          case 5:
            listBooksByLanguage();
            break;
          case 6:
            listAliveAuthors();
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

  private void listAliveAuthors() {
    // Preguntar al usuario por el año
    System.out.println("Ingrese el año que desea buscar");
    int year = Integer.parseInt(scan.nextLine());
    // Buscar datos en la base de datos
    List<AuthorEntity> autores = repository.searchAliveAuthorsByYear(year);
    // Mostrar los datos al usuario
    if (autores.isEmpty()){
      System.out.println("No hay autores vivos en ese año dentro de la base de datos");
    } else {
      System.out.println("***Listado de autores vivos en el año " + year + "***");
      autores.forEach(a -> System.out.printf("""
        ******************************************
        - Autor: %s
        - Año de nacimiento: %d
        - Año de fallecimiento: %d %n
        """, a.getName(), a.getBirthYear(), a.getDeathYear()));
    }
  }

  private void listBooksByLanguage() {
    // Preguntar al usuario por los lenguajes
    System.out.println("Seleccione el idioma de los libros que desea ver:\n1. Español\n2. Inglés");
    int opcion = Integer.parseInt(scan.nextLine());
    // Traer datos de la base de datos
    List<BookEntity> books = new ArrayList<>();
    if (opcion == 1) {
      books = repository.findByLanguage("es");
    } else if(opcion == 2){
      books = repository.findByLanguage("en");
    } else {
      System.out.println("Opcion Inválida, intente nuevamente");
    }
    // Mostrar la informacion al usuario
    if (books.isEmpty()){
      System.out.println("No Existen libros en el idioma seleccionado");
    } else {
      books.forEach(b -> System.out.printf("""
        Libro:
          - Titulo: %s
          - Autor: %s
          - Cantidad de descargas: %d %n
        """, b.getTitle(), b.getAuthor().getName(), b.getDownloadCount()));
    }

  }

  private void listAuthorsByYears() {
    System.out.println("Ingrese el año de nacimiento del autor");
    int birthYear = Integer.parseInt(scan.nextLine());

    System.out.println("Ingrese el año de fallecimiento del autor");
    int deathYear = Integer.parseInt(scan.nextLine());

    List<AuthorEntity> authors = repository.searAuthorsByYear(birthYear, deathYear);

    authors.forEach(a -> System.out.println("\nNombre: " + a.getName()));
  }

  private void listAllAuthors() {
    List<AuthorEntity> authors = repository.searchAllAuthors();
    authors.forEach(a -> System.out.printf("""
      Autor:
        - Nombre: %s
        - Año de Nacimiento: %s
        - Año de Fallecimiento: %s %n
      """, a.getName(), a.getBirthYear(), a.getDeathYear()));
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
      if (response.results().isEmpty()) {
        System.out.println("No se encuentra el libro");

      } else {
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
      }


    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    } catch (DataIntegrityViolationException e) {
      System.out.println("Ya existe el libro en la base de datos");
    }

  }
}
