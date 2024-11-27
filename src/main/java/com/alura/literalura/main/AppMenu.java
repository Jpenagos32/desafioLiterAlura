package com.alura.literalura.main;

import com.alura.literalura.model.BooksRecord;
import com.alura.literalura.model.Results;
import com.alura.literalura.services.ApiConnection;
import com.alura.literalura.services.DataConvert;

import java.io.IOException;
import java.util.Scanner;

public class AppMenu {

  private final String API_URL = "https://gutendex.com/books/";
  private final Scanner scan = new Scanner(System.in);
  private final ApiConnection api = new ApiConnection();
  private final DataConvert converter = new DataConvert();

  public void showMenu() {
    var option = -1;

    while (option != 0) {

      System.out.println("""
                
        Ingrese la opcion que desea realizar:
        1. Buscar libro por nombre
                
        0. Salir
        """);

      try {

        option = Integer.parseInt(scan.nextLine());

        switch (option) {
          case 1:
            searchBookByName();
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

  private void searchBookByName() {
    try {
      // Solicitar el nombre del libro
      System.out.println("Ingrese el nombre del libro que desea buscar");
      String bookName = scan.nextLine();

      // Ir a la api y obtener el libro
      var json = api.getApiData(this.API_URL + "?search=" + bookName.replace(" ", "+"));

      // Convertir el resultado en objeto Java
      BooksRecord response = converter.convertFromJson(json, BooksRecord.class);

      // Obtener el primer resultado de la respuesta
      Results firstResult = response.results().getFirst();

      // TODO guardar el libro en la base de datos

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
    }
  }
}
