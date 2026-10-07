package org.example;

import org.example.clases.Usuario;
import org.example.clasesDAO.UsuarioDAO;
import org.example.conexiones.DatabaseConnection;
import org.example.implementaciones.UsuarioDAOImpl;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection bdConexion = new DatabaseConnection();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl(bdConexion);

        //insertarUsuaro
        Usuario nuevoUsuario = new Usuario(4, "Fatima", "Yaagoub", "calle portugal 8", "Consuegra");
        usuarioDAO.insertarUsuario(nuevoUsuario);

        //buscarPorLocalidad
        List<Usuario> usuarioList = usuarioDAO.buscarPorLocalidad("Madridejos");
        System.out.println(usuarioList);

        //actualizarUsuario

        Usuario usuarioNuevo = new Usuario(2, "Fatima", "WARDI", "calle portugal 8", "Madridejos");
        usuarioDAO.actualizarUsuario(usuarioNuevo);


        //eliminarUsuario
        usuarioDAO.eliminarUsuario(2);


    }
}