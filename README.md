# desafioLiterAlura

Este desafio Está construido utilizando Java con Spring

## Tabla de contenidos

- [Notas aclaratorias](#notas-aclaratorias)
- [Buscar libro por nombre](#1-buscar-libro-por-nombre)
- [Listar todos los libros buscados](#2-listar-todos-los-libros-buscados)
- [Listar autores de los libros buscados](#3-listar-autores-de-los-libros-buscados)
- [Listar autores vivos por rango de años](#4-listar-autores-vivos-por-rango-de-años)
- [Listar libros por idioma](#5-listar-libros-por-idioma)
- [Listar autores vivos en determinado año](#6-listar-autores-vivos-en-determinado-año)
- [Salir](#7-salir)

## Listado de opciones disponibles en la aplicación

### Notas aclaratorias
Para poder hacer uso de cualquiera de las siguientes opciones será necesario indicarla usando el número correspondiente a la opción, dicho número se indica en la pantalla al momento de correr la aplicación.

El punto de entrada de la aplicación se encuentra en: `src/main/java/com.alura.literalura/LiteraluraApplication.java`, para poder correr la aplicación se deberá entrar en el archivo y presionar el boton `run`

### 1. Buscar libro por nombre
- Se debe proveer el nombre de un libro o de un autor, o los dos y así se obtendrá el resultado deseado Junto con los nombres de los autores, fechas de nacimiento y de fallecimiento. Esta opcion lo que hace es hacer un llamado a la api de [Gutendex](https://gutendex.com/books/) la cual retorna los datos del libro solicitado en caso de que se encuentre, en caso de no encontrarse dará como resultado un mensaje de aviso. 

### 2. Listar todos los libros buscados
- Al utilizar esta opcion se mostrará en la terminal un listado de los libros previamente buscados
usando la opción 1

### 3. Listar autores de los libros buscados

- Al utilizar esta opcion se mostrarán todos los autores que se hayan guardado en la base de datos al momento de seleccionar esta opción

### 4. Listar autores vivos por rango de años

- Esta opción solicitará el año de nacimiento y el año de fallecimiento del autor y mostrará un listado de autores vivos en ese rango de años

### 5. Listar libros por idioma

- Esta opción primero mostrará una lista preguntando por el idioma que desea buscar, si encuentra algun libro con el idioma que seleccionó mostrará el listado de los libros disponibles en ese idioma, de lo contrario mostrará un mensaje indicando que no hay ningun libro en ese idioma dentro de la base de datos

### 6. Listar autores vivos en determinado año

- Esta opción pedirá que se ingrese un año para poder realizar la busqueda, en caso de encontrar datos dentro de la base de datos mostrará un listado de todos los autores vivos en ese año

### 7. Salir

- Esta opción detendrá la ejecución del programa mostrando un mensaje al respecto