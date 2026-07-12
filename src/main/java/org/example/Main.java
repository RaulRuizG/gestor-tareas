package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Repositorio repositorio = new TareaRepositorio("tareas.db");
        GestorTareas gestor = new GestorTareas(repositorio);
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Añadir tarea");
            System.out.println("2. Listar tareas");
            System.out.println("3. Marcar tarea como completada");
            System.out.println("4. Borrar tarea");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Descripción: ");
                    String descripcion = scanner.nextLine();
                    Tarea nuevaTarea = gestor.añadir(titulo, descripcion);
                    System.out.println("Tarea añadida correctamente con ID: " + nuevaTarea.getId());
                    break;

                case 2:
                    List<Tarea> tareas = gestor.listar();
                    if (tareas.isEmpty()) {
                        System.out.println("No hay tareas.");
                    } else {
                        for (Tarea tarea : tareas) {
                            System.out.println(tarea);
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID de la tarea a marcar como completada: ");
                    int idCompletar = Integer.parseInt(scanner.nextLine());
                    boolean marcada = gestor.marcarCompletada(idCompletar);
                    System.out.println(marcada ? "Tarea marcada como completada." : "No se encontró esa tarea.");
                    break;

                case 4:
                    System.out.print("ID de la tarea a borrar: ");
                    int idBorrar = Integer.parseInt(scanner.nextLine());
                    boolean borrada = gestor.borrar(idBorrar);
                    System.out.println(borrada ? "Tarea borrada." : "No se encontró esa tarea.");
                    break;

                case 5:
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }
}