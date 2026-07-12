package org.example;

import java.util.List;

public interface Repositorio {
    Tarea guardar(String titulo, String descripcion);
    List<Tarea> listarTodas();
    boolean marcarCompletada(int id);
    boolean borrar(int id);
}