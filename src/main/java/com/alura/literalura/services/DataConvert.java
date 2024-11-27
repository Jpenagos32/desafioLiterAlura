package com.alura.literalura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConvert {

  public <T> T convertFromJson(String json, Class<T> pClass) {
    // Object mapper hace parte de jackson databind
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // Esto es util para poder convertir el JSON en objetos java "deserializacion"
      // Para este caso convierte un json que entra por parametro como string y lo convertimos en
      // la clase que tambien entra por parametro (por eso es una clase generica "T")
      return objectMapper.readValue(json, pClass);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
