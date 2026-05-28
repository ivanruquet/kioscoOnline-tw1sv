package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Usuario.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario.ServicioLogin;
import com.tallerwebi.dominio.Usuario.ServicioLoginImpl;
import com.tallerwebi.dominio.Usuario.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ServicioLoginTest {

  private ServicioLogin servicioLogin;
  private RepositorioUsuario repositorioUsuarioMock;

  @BeforeEach
  public void init() {
    this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
    this.servicioLogin = new ServicioLoginImpl(this.repositorioUsuarioMock);
  }

  @Test
  public void consultarUsuarioEnLoginDeberiaLlamarAlRepositorio() {
    // preparacion
    String email = "test@test.com";
    String password = "password";
    Usuario usuarioEsperado = new Usuario();
    when(this.repositorioUsuarioMock.buscarUsuarioLogin(email, password))
      .thenReturn(usuarioEsperado);

    // ejecucion
    Usuario usuarioObtenido = this.servicioLogin.consultarUsuarioLogin(email, password);

    // validacion
    assertThat(usuarioObtenido, equalTo(usuarioEsperado));
    verify(this.repositorioUsuarioMock, times(1)).buscarUsuarioLogin(email, password);
  }

  @Test
  public void registrarUsuarioSiNoExisteDeberiaGuardarlo() throws UsuarioExistente {
    // preparacion
    Usuario usuario = new Usuario();
    usuario.setEmail("nuevo@test.com");
    usuario.setPassword("password");
    usuario.setDni(123456L);
    when(this.repositorioUsuarioMock.existeUsuarioPorMail(usuario.getEmail())).thenReturn(false);
    when(this.repositorioUsuarioMock.existeUsuarioPorDni(usuario.getDni())).thenReturn(false);

    // ejecucion
    this.servicioLogin.registrar(usuario);

    // validacion
    verify(this.repositorioUsuarioMock, times(1)).guardar(usuario);
  }

  @Test
  public void registrarUsuarioSiExisteMailDeberiaLanzarExcepcion() {
    Usuario usuario = new Usuario();
    usuario.setEmail("existe@test.com");
    usuario.setDni(999999L);
    usuario.setPassword("123");
    when(this.repositorioUsuarioMock.existeUsuarioPorMail(usuario.getEmail())).thenReturn(true); // simula que ya hay alguien con ese mail
    when(this.repositorioUsuarioMock.existeUsuarioPorDni(usuario.getDni())).thenReturn(false);

    // ejecucion y validacion
    assertThrows(UsuarioExistente.class, () -> this.servicioLogin.registrar(usuario));
    verify(this.repositorioUsuarioMock, times(0)).guardar(usuario);
  }

  @Test
  public void registrarUsuarioSiExisteDniDeberiaLanzarExcepcion() {
    // preparacion
    Usuario usuario = new Usuario();
    usuario.setEmail("existe@test.com");
    usuario.setDni(999999L);
    usuario.setPassword("123");
    when(this.repositorioUsuarioMock.existeUsuarioPorMail(usuario.getEmail())).thenReturn(false);
    when(this.repositorioUsuarioMock.existeUsuarioPorDni(usuario.getDni())).thenReturn(true); // simula que ya hay alguien con ese DNI

    // ejecucion y validacion
    assertThrows(UsuarioExistente.class, () -> this.servicioLogin.registrar(usuario));
    verify(this.repositorioUsuarioMock, times(0)).guardar(usuario);
  }

  @Test
  public void CambiarContraConMailVerificadoDeberiaModificarUsuario() {
    String email = "ro@test.com";
    String nuevaClave = "nuevaClave";
    Usuario usuEncontrado = new Usuario();

    usuEncontrado.setEmail(email);
    when(repositorioUsuarioMock.buscarUsuarioPorEmail(email)).thenReturn(usuEncontrado);

    servicioLogin.cambiarContrasenia(email, nuevaClave);

    verify(repositorioUsuarioMock, times(1)).modificar(usuEncontrado);
    assertThat(usuEncontrado.getPassword(), equalTo(nuevaClave));
  }
}
