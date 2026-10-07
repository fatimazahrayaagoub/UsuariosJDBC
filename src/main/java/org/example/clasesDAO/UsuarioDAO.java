package org.example.clasesDAO;

import org.example.clases.Usuario;

import java.util.List;

public interface UsuarioDAO {
    List<Usuario> buscarPorLocalidad(String localidad);
    void actualizarUsuario(Usuario usuario);
    void insertarUsuario(Usuario usuario);
    void eliminarUsuario(int cod);
}
