package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TareaRepositorio implements Repositorio{
    private final String urlConexion;

    public TareaRepositorio(String rutaBaseDatos) {
        this.urlConexion = "jdbc:sqlite:" + rutaBaseDatos;
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS tareas ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titulo TEXT NOT NULL,"
                + "descripcion TEXT,"
                + "completada INTEGER NOT NULL"
                + ");";

        try (Connection conexion = DriverManager.getConnection(urlConexion);
             Statement stmt = conexion.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la tabla de tareas", e);
        }
    }

    public Tarea guardar(String titulo, String descripcion) {
        String sql = "INSERT INTO tareas (titulo, descripcion, completada) VALUES (?, ?, 0)";

        try (Connection conexion = DriverManager.getConnection(urlConexion);
             PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, titulo);
            stmt.setString(2, descripcion);
            stmt.executeUpdate();

            try (ResultSet claves = stmt.getGeneratedKeys()) {
                if (claves.next()) {
                    int id = claves.getInt(1);
                    return new Tarea(id, titulo, descripcion);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la tarea", e);
        }
        return null;
    }

    public List<Tarea> listarTodas() {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT id, titulo, descripcion, completada FROM tareas";

        try (Connection conexion = DriverManager.getConnection(urlConexion);
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tarea tarea = new Tarea(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descripcion")
                );
                if (rs.getInt("completada") == 1) {
                    tarea.marcarCompletada();
                }
                tareas.add(tarea);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las tareas", e);
        }
        return tareas;
    }

    public boolean marcarCompletada(int id) {
        String sql = "UPDATE tareas SET completada = 1 WHERE id = ?";

        try (Connection conexion = DriverManager.getConnection(urlConexion);
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al marcar la tarea como completada", e);
        }
    }

    public boolean borrar(int id) {
        String sql = "DELETE FROM tareas WHERE id = ?";

        try (Connection conexion = DriverManager.getConnection(urlConexion);
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al borrar la tarea", e);
        }
    }
}