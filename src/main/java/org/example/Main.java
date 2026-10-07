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
        ResultSet rts2 = null;



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
            String sql7 = """
                    CREATE TABLE IF NOT EXISTS Telefonos(
                    cod INTEGER,
                    telefono VARCHAR(100),
                    PRIMARY KEY (cod,telefono),
                    FOREIGN KEY (cod) REFERENCES Usuarios(cod)
                    )
                    """;

            stm = connection.createStatement();
            stm.executeUpdate(sql);
            stm.executeUpdate(sql7);

            // Pedir localidad por teclado
            Scanner sc = new Scanner(System.in);

            System.out.println("introduce la localidad que quieres");
            String locali = sc.nextLine();


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

            String sql8 = """
                    INSERT INTO Telefonos(cod,telefono)
                    VALUES (1, '600123456')
                    """;

            String sql9 = """
                    INSERT INTO Telefonos(cod, telefono)
                    VALUES (1, '611234567')
                    """;

            String sql10 = """
                    INSERT INTO Telefonos(cod, telefono)
                    VALUES (2, '622345678')
                    """;

            String sql11 = """
                    INSERT INTO Telefonos(cod, telefono)
                    VALUES (3, '633456789')
                    """;

            String sql12 = """
                    INSERT INTO Telefonos(cod, telefono)
                    VALUES (3, '644567890')
                    """;

            // SELECT por localidad
            String sql2 = """
                    SELECT * FROM Usuarios WHERE localidad = ?
                    """;


            // Contar usuarios por localidad
            String sql6 = """
                    SELECT localidad, COUNT(*) AS cantidadUsuarios
                    FROM Usuarios
                    GROUP BY localidad
                    """;

            String sql13= """
                    SELECT u.cod,u.nombre,u.apellidos,u.direccion,u.localidad,t.telefono
                    FROM Usuarios u JOIN Telefonos t
                    ON u.cod=t.cod
                    """;

            // Insertar usuarios
            stm.executeUpdate(sql3);
            stm.executeUpdate(sql4);
            stm.executeUpdate(sql5);
            stm.executeUpdate(sql8);
            stm.executeUpdate(sql9);
            stm.executeUpdate(sql10);
            stm.executeUpdate(sql11);
            stm.executeUpdate(sql12);




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

            //EJECUTAR SELECT DE TELEFONOS
            rts2=stm.executeQuery(sql13);

            System.out.println("usario con su telefono");
            while(rts2.next()){

                int cod = rts2.getInt("cod");
                String nombre = rts2.getString("nombre");
                String apellidos = rts2.getString("apellidos");
                String direccion = rts2.getString("direccion");
                String localidad = rts2.getString("localidad");
                String telefono=rts2.getString("telefono");

                System.out.println(
                        "codigo: " + cod +
                                " - nombre: " + nombre +
                                " - apellidos: " + apellidos +
                                " - direccion: " + direccion +
                                " - localidad: " + localidad +
                                " - telefono:  " +telefono
                );

            }


            System.out.println("\nresultados recorridos con exito");

        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {

            try {
                if (rts2 !=null) rts2.close();
                if (rts1 != null) rts1.close();
                if (rts != null) rts.close();
                if (pstm != null) pstm.close();
                if (stm != null) stm.close();
                if (connection != null) connection.close();

            } catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }
    }
}