package org.example.implementaciones;

import org.example.conexiones.DatabaseConnection;
import org.example.clases.Usuario;
import org.example.clasesDAO.UsuarioDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {
    private final DatabaseConnection databaseConnection;

    public UsuarioDAOImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void insertarUsuario(Usuario usuario) {
        String sql = """
                INSERT INTO Usuarios (cod,nombre,apellidos,direccion,localidad)
                VALUES(?,?,?,?,?)
                """;
        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, usuario.getCod());

            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellidos());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getLocalidad());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminarUsuario(int cod) {
        String sql = """
                DELETE FROM Usuarios WHERE cod=?
                """;

        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, cod);
            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        String sql = """
                UPDATE Usuarios
                SET nombre=?,apellidos=?,direccion=?, localidad=?
                WHERE cod=?
                
                """;

        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement ps = conex.prepareStatement(sql)) {


            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getDireccion());
            ps.setString(4, usuario.getLocalidad());
            ps.setInt(5, usuario.getCod());

            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Usuario> buscarPorLocalidad(String localidad) {

        List<Usuario> lista = new ArrayList<>();
        String sql = """
                SELECT* FROM Usuarios WHERE localidad=?
                """;

        try (Connection conex = databaseConnection.getConnection();
             PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setString(1, localidad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getInt("cod"),
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getString("direccion"),
                            rs.getString("localidad")
                    );
                    lista.add(usuario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }


}
