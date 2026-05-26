package com.tallerwebi.dominio.Usuario;

public interface RepositorioUsuario {
  Usuario buscarUsuarioLogin(String email, String password);
  void guardar(Usuario usuario);
  Boolean existeUsuario_PorMail(String email);
  Boolean existeUsuario_PorDNI(Long dni);
  void modificar(Usuario usuario);

  Usuario buscarUsuarioPorId(Long id);
}
