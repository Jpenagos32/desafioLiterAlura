package com.alura.literalura;

import com.alura.literalura.main.AppMenu;
import com.alura.literalura.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* Al implementar CommandLineRunner le estamos diciendo que será una aplicacion de linea de comandos
* */
@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
  @Autowired
  private BooksRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(LiteraluraApplication.class, args);
  }

	@Override
	public void run(String ...args) {
    AppMenu menu = new AppMenu(repository);
    menu.showMenu();
  }
}
