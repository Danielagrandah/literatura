package com.aluracursos.literatura.principal;

import com.aluracursos.literatura.model.Datos;
import com.aluracursos.literatura.model.DatosLibro;
import com.aluracursos.literatura.service.ConsumoAPI;
import com.aluracursos.literatura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private List<DatosLibro> datosLibroList = new ArrayList<>();

    public void muestraMenu() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        var opcion = -1;
        while (opcion != 0 ){
            var menu = """
                    1 - Buscar Libro por titulo
                    2 - Listar libros Guardados
                    3 - listar autores registrados
                    4 - Listar autores vivos en un año determinado
                    5 - Listar libros por idioma
                    
                    
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarlibroPorTitulo();
                    break;
                case 2:
                    listarLibrosGuardados();
                    break;
               /* 
                case 3:
                     listarAutoresRegistrados();
                    break;
                case 4:
                    ListarAutoresVivosPorAño();
                    break;
                case 5:
                    listarAutoresPorIdioam();
                    break;*/

                case 0:
                    System.out.println("Cerrando la aplicación");
                default:
                    System.out.println("Opción invalida");
            }

        }
    }

    private void buscarlibroPorTitulo() {
        System.out.println("*******************\n" + "Por favor escribe el nombre del libro que deaseas buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()){
            System.out.println("Libro Encontrado");
            var libroEncontrado = libroBuscado.get();
            datosLibroList.add(libroBuscado.get());

        }else{
            System.out.println("Libro no encontrado");
        }
    }



    /// Metodo 2. ver los libros guardados en la lista
    private void listarLibrosGuardados() {
        if (datosLibroList.isEmpty()) {
            System.out.println("No hay libros guardados en la lista.");
        } else {
            System.out.println("Lista de libros guardados:");
            datosLibroList.forEach(libro -> {
                System.out.println("Título: " + libro.titulo());
                System.out.println("Idiomas: " + String.join(", ", libro.idiomas()));
                System.out.println("Descargas: " + libro.numeroDeDescargas());
                System.out.println("Autores: " + (libro.autor().isEmpty() ? "No especificados" :
                        libro.autor().stream().map(a -> a.nombre()).collect(Collectors.joining(", "))));
                System.out.println("-------------");
            });
        }
    }



      /*   //Top 10 libros más descargados
        System.out.println("Top 10 libros más descargados");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibro::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);*/

}



