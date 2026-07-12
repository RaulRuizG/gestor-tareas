package org.example;

import java.util.ArrayList;
import java.util.List;

public class RepositorioEnMemoria implements Repositorio {
    private final List<Tarea> tareas = new ArrayList<>();
    private int siguienteId = 1;

    @Override
    public Tarea guardar(String titulo, String descripcion) {
        Tarea nuevaTarea = new Tarea(siguienteId, titulo, descripcion);
        tareas.add(nuevaTarea);
        siguienteId++;
        return nuevaTarea;
    }

    @Override
    public List<Tarea> listarTodas() {
        return tareas;
    }

    @Override
    public boolean marcarCompletada(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                tarea.marcarCompletada();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean borrar(int id) {
        return tareas.removeIf(tarea -> tarea.getId() == id);
    }
}