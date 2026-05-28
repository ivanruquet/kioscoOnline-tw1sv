package com.tallerwebi.dominio.Usuario;

public interface RepositorioUsuario {
  Usuario buscarUsuarioLogin(String email, String password);
  void guardar(Usuario usuario);
  Boolean existeUsuarioPorMail(String email);
  Boolean existeUsuarioPorDni(Long dni);
  void modificar(Usuario usuario);
  Usuario buscarUsuarioPorId(Long id);
  Usuario buscarUsuarioPorEmail(String email);
}
