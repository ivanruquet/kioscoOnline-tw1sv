package com.tallerwebi.dominio.Usuario;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

  private final RepositorioUsuario repositorioUsuario;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public ServicioLoginImpl(RepositorioUsuario repositorioUsuario, PasswordEncoder passwordEncoder) {
    this.repositorioUsuario = repositorioUsuario;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Usuario consultarUsuarioLogin(String email, String password) {
    Usuario usuario = repositorioUsuario.buscarUsuarioPorEmail(email);
    if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
      return usuario;
    }
    return null;
  }

  @Override
  public void registrar(Usuario usuario) throws UsuarioExistente {
    if (this.usuarioYaExiste(usuario)) { //el metodo boolean
      throw new UsuarioExistente();
    }
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // hasheo
    repositorioUsuario.guardar(usuario);
  }

  @Override
  public void cambiarContrasenia(String email, String nuevaClave) {
    Usuario usuario = repositorioUsuario.buscarUsuarioPorEmail(email);
    if (usuario != null) {
      usuario.setPassword(passwordEncoder.encode(nuevaClave)); // hasheo
      repositorioUsuario.modificar(usuario);
    }
  }

  @Override
  public Boolean usuarioYaExiste(Usuario usuario) {
    return (
      repositorioUsuario.existeUsuarioPorMail(usuario.getEmail()) ||
      repositorioUsuario.existeUsuarioPorDni(usuario.getDni())
    );
  }
}
