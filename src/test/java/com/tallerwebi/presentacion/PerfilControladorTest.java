package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.ServicioHijo;
import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

public class PerfilControladorTest {

  private Usuario usuarioMock;
  private PerfilControlador perfilControlador;
  private HttpSession sessionMock;
  private ServicioHijo servicioHijoMock;

  @BeforeEach
  public void init() {
    servicioHijoMock = Mockito.mock(ServicioHijo.class);
    perfilControlador = new PerfilControlador(servicioHijoMock);
    usuarioMock = Mockito.mock(Usuario.class);
    sessionMock = Mockito.mock(HttpSession.class);
  }

  @Test
  public void siNoHayUsuarioRegistradoAlApretarPerfilIrAlLogin() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(null);
    ModelAndView modelAndView = perfilControlador.irAlPerfil(sessionMock);
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
  }

  @Test
  public void siHayUsuarioRegistradoAlApretarPerfilIrAlPerfil() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    ModelAndView modelAndView = perfilControlador.irAlPerfil(sessionMock);
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("perfil"));
  }

  @Test
  public void elPerfilDebeMostrarNombreDeUsuario() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    when(usuarioMock.getNombre()).thenReturn("nombrePrueba");
    ModelAndView modelAndView = perfilControlador.mostrarDatosUsuario(sessionMock);
    assertThat(
      ((Usuario) modelAndView.getModel().get("usuario")).getNombre(),
      equalToIgnoringCase("nombrePrueba")
    );
  }

  @Test
  public void elPerfilDebeMostrarApellidoDeUsuario() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    when(usuarioMock.getApellido()).thenReturn("apellidoPrueba");
    ModelAndView modelAndView = perfilControlador.mostrarDatosUsuario(sessionMock);
    assertThat(
      ((Usuario) modelAndView.getModel().get("usuario")).getApellido(),
      equalToIgnoringCase("apellidoPrueba")
    );
  }

  @Test
  public void elPerfilDebeMostrarEmailDeUsuario() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    when(usuarioMock.getEmail()).thenReturn("email");
    ModelAndView modelAndView = perfilControlador.mostrarDatosUsuario(sessionMock);
    assertThat(
      ((Usuario) modelAndView.getModel().get("usuario")).getEmail(),
      equalToIgnoringCase("email")
    );
  }

  @Test
  public void elPerfilDebeMostrarFotoDePerfilDeUsuario() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    when(usuarioMock.getFotoPerfil()).thenReturn("foto.jpg");
    ModelAndView modelAndView = perfilControlador.mostrarDatosUsuario(sessionMock);
    assertThat(
      ((Usuario) modelAndView.getModel().get("usuario")).getFotoPerfil(),
      equalToIgnoringCase("foto.jpg")
    );
  }

  @Test
  public void elPerfilDebeMostrarLaCantDeHijosDelUsuario() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    //simulo un hijo
    List<Hijo> hijoMock = Arrays.asList(Mockito.mock(Hijo.class));
    when(usuarioMock.getHijos()).thenReturn(hijoMock); //cuando llamo a getHijos le pido que retorne el mock

    ModelAndView modelAndView = perfilControlador.irAlPerfil(sessionMock);

    List<Hijo> hijosObtenidos = ((Usuario) modelAndView.getModel().get("usuario")).getHijos();

    assertThat(hijosObtenidos.size(), equalTo(1));
  }

  @Test
  public void siNoTieneHijosDebeMostrarUnMensaje() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

    when(usuarioMock.getHijos()).thenReturn(null); //cuando llamo a getHijos le pido que retorne null

    ModelAndView modelAndView = perfilControlador.irAlPerfil(sessionMock);

    assertThat(
      (String) modelAndView.getModel().get("mensajeError"),
      equalToIgnoringCase("Aún no tenés hijos registrados")
    );
  }

  @Test
  public void elPerfilDebeMostrarLaInfoDeLosHijos() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

    Hijo hijoMock1 = Mockito.mock(Hijo.class);
    when(hijoMock1.getNombre()).thenReturn("Santiago");

    Hijo hijoMock2 = Mockito.mock(Hijo.class);
    when(hijoMock2.getNombre()).thenReturn("Romina");

    List<Hijo> hijosSimulados = Arrays.asList(hijoMock1, hijoMock2);

    when(servicioHijoMock.obtenerHijosPorUsuario(usuarioMock.getId())).thenReturn(hijosSimulados);

    ModelAndView modelAndView = perfilControlador.irAlPerfil(sessionMock);

    List<Hijo> hijosObtenidos = (List<Hijo>) modelAndView.getModel().get("hijos");

    assertThat(hijosObtenidos.get(0).getNombre(), equalToIgnoringCase("Santiago"));
    assertThat(hijosObtenidos.get(1).getNombre(), equalToIgnoringCase("Romina"));
  }
}
