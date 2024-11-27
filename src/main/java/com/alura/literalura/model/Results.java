package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Results(
  Long id,
  String title,
  List<Authors> authors,
  List<String> languages,
  @JsonAlias("download_count") Long downloadCount
) {
}
