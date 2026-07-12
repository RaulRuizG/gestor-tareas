package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GestorTareasTest {

    @Test
    void testAñadirTarea() {
        GestorTareas gestor = new GestorTareas(new RepositorioEnMemoria());
        Tarea tarea = gestor.añadir("Estudiar Física", "Repasar el tema 3");

        assertEquals(1, tarea.getId());
        assertEquals("Estudiar Física", tarea.getTitulo());
        assertFalse(tarea.isCompletada());
    }

    @Test
    void testIdsIncrementales() {
        GestorTareas gestor = new GestorTareas(new RepositorioEnMemoria());
        Tarea tarea1 = gestor.añadir("Tarea A", "Descripción A");
        Tarea tarea2 = gestor.añadir("Tarea B", "Descripción B");
        Tarea tarea3 = gestor.añadir("Tarea C", "Descripción C");

        assertEquals(1, tarea1.getId());
        assertEquals(2, tarea2.getId());
        assertEquals(3, tarea3.getId());
    }

    @Test
    void testListarTareas() {
        GestorTareas gestor = new GestorTareas(new RepositorioEnMemoria());
        gestor.añadir("Tarea 1", "Descripción 1");
        gestor.añadir("Tarea 2", "Descripción 2");

        List<Tarea> tareas = gestor.listar();

        assertEquals(2, tareas.size());
    }

    @Test
    void testMarcarCompletadaExistente() {
        GestorTareas gestor = new GestorTareas(new RepositorioEnMemoria());
        Tarea tarea = gestor.añadir("Tarea 1", "Descripción 1");

        boolean resultado = gestor.marcarCompletada(tarea.getId());

        assertTrue(resultado);
        assertTrue(tarea.isCompletada());
    }

    @Test
    void testMarcarCompletadaInexistente() {
        GestorTareas gestor = new GestorTareas(new RepositorioEnMemoria());

        boolean resultado = gestor.marcarCompletada(999);

        assertFalse(resultado);
    }

    @Test
    void testBorrarTareaExistente() {
        GestorTareas gestor = new GestorTareas(new RepositorioEnMemoria());
        Tarea tarea = gestor.añadir("Tarea 1", "Descripción 1");

        boolean resultado = gestor.borrar(tarea.getId());

        assertTrue(resultado);
        assertEquals(0, gestor.listar().size());
    }

    @Test
    void testBorrarTareaInexistente() {
        GestorTareas gestor = new GestorTareas(new RepositorioEnMemoria());

        boolean resultado = gestor.borrar(999);

        assertFalse(resultado);
    }
}