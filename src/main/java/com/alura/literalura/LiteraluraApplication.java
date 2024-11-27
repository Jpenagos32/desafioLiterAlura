package com.alura.literalura;

import com.alura.literalura.main.AppMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* Al implementar CommandLineRunner le estamos diciendo que será una aplicacion de linea de comandos
* */
@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(LiteraluraApplication.class, args);
  }

	@Override
	public void run(String ...args) {
    AppMenu menu = new AppMenu();
    menu.showMenu();
  }
}
