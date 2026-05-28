package com.tallerwebi.dominio.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario {

  RepositorioUsuario repositorioUsuario;

  @Autowired
  public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
    this.repositorioUsuario = repositorioUsuario;
  }

  @Override
  public void actualizarMail(Long id, String mailNuevo) {
    Usuario usuario = repositorioUsuario.buscarUsuarioPorId(id);
    usuario.setEmail(mailNuevo);
    repositorioUsuario.modificar(usuario);
  }

  @Override
  public void actualizarCelular(Long id, Long celular) {
    Usuario usuario = repositorioUsuario.buscarUsuarioPorId(id);
    usuario.setCelular(celular);
    repositorioUsuario.modificar(usuario);
  }

  @Override
  public void actualizarFoto(Long id, String fotoPerfil) {
    Usuario usuario = repositorioUsuario.buscarUsuarioPorId(id);
    usuario.setFotoPerfil(fotoPerfil);
    repositorioUsuario.modificar(usuario);
  }

  @Override
  public Usuario buscarPorId(Long id) {
    return repositorioUsuario.buscarUsuarioPorId(id);
  }
}
