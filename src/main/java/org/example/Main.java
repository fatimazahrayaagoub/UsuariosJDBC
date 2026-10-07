package org.example;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:prueba.db";
        Connection connection = null;
        Statement stm = null;
        ResultSet rts = null;

        try {
            connection = DriverManager.getConnection(url);
            System.out.println("base de datos conectada con exito");
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

            String sql2 = """
                    SELECT*  FROM Usuarios WHERE localidad="Madridejos"
                    """;
            String sql3= """
                    INSERT INTO Usuarios(cod,nombre,apellidos,direccion,localidad)VALUES
                    (1, 'Juan', 'García López', 'Calle Mayor 10', 'Madridejos')
                    """;
            String sql4= """
                    INSERT INTO Usuarios(cod,nombre,apellidos,direccion,localidad)VALUES
                    (2, 'Ana', 'Martínez Pérez', 'Calle Sol 5', 'Toledo')
                    
                    """;
            String sql5= """
                    INSERT INTO Usuarios(cod,nombre,apellidos,direccion,localidad)VALUES
                    (3, 'Pedro', 'Sánchez Ruiz', 'Calle Real 20', 'Madridejos')
                    """;
            stm.executeUpdate(sql3);
            stm.executeUpdate(sql4);
            stm.executeUpdate(sql5);



            rts=stm.executeQuery(sql2);
            System.out.println("resultados recorridos con exito");

            while (rts.next()){
                int cod=rts.getInt("cod");
                String nombre =rts.getString("nombre");
                String apellidos=rts.getString("apellidos");
                String direccion=rts.getString("direccion");
                String localidad=rts.getString("localidad");

                System.out.println("codigo: "+cod+"  -nombre: "+nombre+"  -apellidos: "+apellidos+
                        "  -direccion: "+direccion+"  -localidad: "+localidad);
            }





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (rts!=null) rts.close();
                if (stm!=null) stm.close();
                if(connection!=null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}