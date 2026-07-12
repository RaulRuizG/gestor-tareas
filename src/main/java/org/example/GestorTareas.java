package org.example;

import java.util.List;

public class GestorTareas {
    private final Repositorio repositorio;

    public GestorTareas(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Tarea añadir(String titulo, String descripcion) {
        return repositorio.guardar(titulo, descripcion);
    }

    public List<Tarea> listar() {
        return repositorio.listarTodas();
    }

    public boolean marcarCompletada(int id) {
        return repositorio.marcarCompletada(id);
    }

    public boolean borrar(int id) {
        return repositorio.borrar(id);
    }
}