package com.tallerwebi.dominio.Usuario;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

  private final RepositorioUsuario repositorioUsuario;

  @Autowired
  public ServicioLoginImpl(RepositorioUsuario repositorioUsuario) {
    this.repositorioUsuario = repositorioUsuario;
  }

  @Override
  public Usuario consultarUsuarioLogin(String email, String password) {
    return repositorioUsuario.buscarUsuarioLogin(email, password);
  }

  @Override
  public void registrar(Usuario usuario) throws UsuarioExistente {
    if (this.usuarioYaExiste(usuario)) { // ← directamente el boolean
      throw new UsuarioExistente();
    }
    repositorioUsuario.guardar(usuario);
  }

  @Override
  public Boolean usuarioYaExiste(Usuario usuario) {
    return (
      repositorioUsuario.existeUsuario_PorMail(usuario.getEmail()) ||
      repositorioUsuario.existeUsuario_PorDNI(usuario.getDni())
    );
  }
}
