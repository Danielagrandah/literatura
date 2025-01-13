package com.aluracursos.literatura.service;

import java.util.Optional;

public class LibroService {

    public String procesarTitulo(String titulo) {
        try {
            // Usa Optional para evitar errores con valores nulos
            return Optional.ofNullable(titulo)
                    .orElse("TÍTULO DESCONOCIDO") // Si el título es nulo, se asigna un valor por defecto
                    .toUpperCase(); // Convierte el título a mayúsculas
        } catch (Exception e) {
            System.out.println("Error al procesar título: " + e.getMessage());
            return "ERROR PROCESANDO TÍTULO";
        }
    }
}
