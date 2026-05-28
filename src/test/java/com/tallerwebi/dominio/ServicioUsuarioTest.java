package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Usuario.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioUsuarioTest {

  private ServicioUsuario servicioUsuario;
  private RepositorioUsuario repositorioUsuarioMock;

  @BeforeEach
  public void init() {
    this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
    this.servicioUsuario = new ServicioUsuarioImpl(this.repositorioUsuarioMock);
  }

  @Test
  public void cambiarMailDebeLLamarAlRepoYCambiarlo() {
    String email = "ro@test.com";
    String nuevoMail = "nuevo@test.com";

    Usuario usuario = new Usuario();
    Long id = 1L;
    usuario.setEmail(email);

    when(repositorioUsuarioMock.buscarUsuarioPorId(id)).thenReturn(usuario);
    servicioUsuario.actualizarMail(id, nuevoMail);

    verify(repositorioUsuarioMock, times(1)).modificar(usuario);
    assertThat(usuario.getEmail(), equalTo(nuevoMail));
  }

  @Test
  public void cambiarCelularDebeLLamarAlRepoYCambiarlo() {
    Long nuevoCelu = 1122334455L;

    Usuario usuario = new Usuario();
    Long id = 1L;
    usuario.setCelular(nuevoCelu);

    when(repositorioUsuarioMock.buscarUsuarioPorId(id)).thenReturn(usuario);
    servicioUsuario.actualizarCelular(id, nuevoCelu);

    verify(repositorioUsuarioMock, times(1)).modificar(usuario);
    assertThat(usuario.getCelular(), equalTo(nuevoCelu));
  }

  @Test
  public void cambiarFotoPerfilDebeLLamarAlRepoYCambiarlo() {
    String foto = "foto.jpg";

    Usuario usuario = new Usuario();
    Long id = 1L;
    usuario.setFotoPerfil(foto);

    when(repositorioUsuarioMock.buscarUsuarioPorId(id)).thenReturn(usuario);
    servicioUsuario.actualizarFoto(id, foto);

    verify(repositorioUsuarioMock, times(1)).modificar(usuario);
    assertThat(usuario.getFotoPerfil(), equalTo(foto));
  }
}
