package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:sqlite:prueba.db";

        Connection connection = null;
        Statement stm = null;
        PreparedStatement pstm = null;
        ResultSet rts = null;
        ResultSet rts1 = null;

        try {

            // Conectar con la base de datos
            connection = DriverManager.getConnection(url);
            System.out.println("base de datos conectada con exito");

            // Crear tabla
            String sql = """
                    CREATE TABLE IF NOT EXISTS Usuarios(
                    cod INTEGER PRIMARY KEY,
                    nombre VARCHAR(100),
                    apellidos VARCHAR(100),
                    direccion VARCHAR(300),
                    localidad VARCHAR(100)
                    )
                    """;

            stm = connection.createStatement();
            stm.executeUpdate(sql);

            // Pedir localidad por teclado
            Scanner sc = new Scanner(System.in);

            System.out.println("introduce la localidad que quieres");
            String locali = sc.nextLine();

            // SELECT por localidad
            String sql2 = """
                    SELECT * FROM Usuarios WHERE localidad = ?
                    """;

            // INSERT usuario 1
            String sql3 = """
                    INSERT INTO Usuarios(cod, nombre, apellidos, direccion, localidad)
                    VALUES
                    (1, 'Juan', 'García López', 'Calle Mayor 10', 'Madridejos')
                    """;

            // INSERT usuario 2
            String sql4 = """
                    INSERT INTO Usuarios(cod, nombre, apellidos, direccion, localidad)
                    VALUES
                    (2, 'Ana', 'Martínez Pérez', 'Calle Sol 5', 'Toledo')
                    """;

            // INSERT usuario 3
            String sql5 = """
                    INSERT INTO Usuarios(cod, nombre, apellidos, direccion, localidad)
                    VALUES
                    (3, 'Pedro', 'Sánchez Ruiz', 'Calle Real 20', 'Madridejos')
                    """;

            // Contar usuarios por localidad
            String sql6 = """
                    SELECT localidad, COUNT(*) AS cantidadUsuarios
                    FROM Usuarios
                    GROUP BY localidad
                    """;

            // Insertar usuarios
            stm.executeUpdate(sql3);
            stm.executeUpdate(sql4);
            stm.executeUpdate(sql5);

            // Ejecutar SELECT por localidad
            pstm = connection.prepareStatement(sql2);
            pstm.setString(1, locali);
            rts = pstm.executeQuery();

            System.out.println("\nUsuarios de la localidad seleccionada:");

            while (rts.next()) {

                int cod = rts.getInt("cod");
                String nombre = rts.getString("nombre");
                String apellidos = rts.getString("apellidos");
                String direccion = rts.getString("direccion");
                String localidad = rts.getString("localidad");

                System.out.println(
                        "codigo: " + cod +
                                " - nombre: " + nombre +
                                " - apellidos: " + apellidos +
                                " - direccion: " + direccion +
                                " - localidad: " + localidad
                );
            }

            // Ejecutar SELECT con GROUP BY
            rts1 = stm.executeQuery(sql6);

            System.out.println("\nCantidad de usuarios por localidad:");

            while (rts1.next()) {

                String localidad = rts1.getString("localidad");
                int cantidad = rts1.getInt("cantidadUsuarios");

                System.out.println(
                        "localidad: " + localidad +
                                " - cantidadUsuarios: " + cantidad
                );
            }

            System.out.println("\nresultados recorridos con exito");

        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {

            try {

                if (rts != null) rts.close();
                if (rts1 != null) rts1.close();
                if (pstm != null) pstm.close();
                if (stm != null) stm.close();
                if (connection != null) connection.close();

            } catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }
    }
}