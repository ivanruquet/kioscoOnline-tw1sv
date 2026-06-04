package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.SubidaDeImgs.ServicioImagenes;
import com.tallerwebi.dominio.Usuario.*;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

public class ServicioUsuarioTest {

  private ServicioUsuario servicioUsuario;
  private ServicioImagenes repositorioImagenesMock;
  private RepositorioUsuario repositorioUsuarioMock;

  @BeforeEach
  public void init() {
    this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
    this.servicioUsuario =
      new ServicioUsuarioImpl(this.repositorioUsuarioMock, repositorioImagenesMock);
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
  public void cambiarFotoPerfilDebeLLamarAlRepoYCambiarlo() throws Exception {
    Usuario usuario = new Usuario();
    Long id = 1L;

    MultipartFile fotoMock = mock(MultipartFile.class);

    when(fotoMock.getOriginalFilename()).thenReturn("foto.jpg");

    when(fotoMock.getInputStream()).thenReturn(new ByteArrayInputStream("imagen".getBytes()));

    when(repositorioUsuarioMock.buscarUsuarioPorId(id)).thenReturn(usuario);

    servicioUsuario.actualizarFoto(id, fotoMock);

    verify(repositorioUsuarioMock, times(1)).modificar(usuario);
    assertThat(usuario.getFotoPerfil(), equalTo("/spring/imagenes/img_Perfiles/foto.jpg"));
  }
}
