package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Usuario;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

public class HomeControladorTest {

  private HomeControlador homeControlador;
  private Usuario usuarioMock;
  private HttpSession sessionMock;

  @BeforeEach
  public void init() {
    homeControlador = new HomeControlador();
    usuarioMock = Mockito.mock(Usuario.class);
    sessionMock = Mockito.mock(HttpSession.class);
  }

  @Test
  public void elHomeDebeMostrarNombreDeUsuario() {
    //preparacion
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

    when(usuarioMock.getNombre()).thenReturn("Rocio");

    //ejecucion
    ModelAndView modelAndView = homeControlador.mostrarDatosHome(sessionMock);

    //validacion

    assertThat(
      ((Usuario) modelAndView.getModel().get("usuario")).getNombre(),
      equalToIgnoringCase("Rocio")
    );
  }

  @Test
  public void elHomeDebeMostrarFotoDePerfilDelUsuario() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    when(usuarioMock.getFotoPerfil()).thenReturn("fotoUsuario.jpeg");

    ModelAndView modelAndView = homeControlador.mostrarDatosHome(sessionMock);

    assertThat(
      ((Usuario) modelAndView.getModel().get("usuario")).getFotoPerfil(),
      equalToIgnoringCase("fotoUsuario.jpeg")
    );
  }
}
