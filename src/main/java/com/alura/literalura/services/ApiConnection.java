package com.alura.literalura.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiConnection {

  /**
   * Este método realiza una peticion a una url de una api para posteriormente retornar imformacion sobre libros
   *
   * @param url Parametro que indica la url a la cual ser redirige la petición
   */
  public String getApiData(String url) throws IOException, InterruptedException {
    // Crear el cliente
    HttpClient client = HttpClient.newHttpClient();

    // Realizar la request hacia la URL deseada
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(url))
      .build();

    // Obtener la respuesta
    HttpResponse<String> response = client
      .send(request, HttpResponse.BodyHandlers.ofString());

    // Retornar el cuerpo de la respuesta
    return response.body();
  }
}
