package com.tallerwebi.dominio.Usuario;

public interface ServicioUsuario {
  void actualizarMail(Long id, String mailNuevo);

  void actualizarCelular(Long id, Long celular);

  void actualizarFoto(Long id, String fotoPerfil);

  Usuario buscarPorId(Long id);
}
