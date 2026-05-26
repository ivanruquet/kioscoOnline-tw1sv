package com.tallerwebi.dominio.Usuario;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface ServicioLogin {
  Usuario consultarUsuarioLogin(String email, String password);

  Boolean usuarioYaExiste(Usuario usuario);

  void registrar(Usuario usuario) throws UsuarioExistente;
}
