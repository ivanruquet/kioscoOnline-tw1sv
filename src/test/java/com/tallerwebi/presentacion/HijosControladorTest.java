package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Hijos.Curso;
import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.ServicioHijo;
import com.tallerwebi.dominio.Usuario.Usuario;
import com.tallerwebi.dominio.excepcion.HijoExistenteException;
import com.tallerwebi.presentacion.HijosControlador;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

public class HijosControladorTest {

  private Usuario usuarioMock;
  private HijosControlador hijosControlador;
  private ServicioHijo servicioHijoMock;
  private HttpSession sessionMock;

  @BeforeEach
  public void init() {
    servicioHijoMock = mock(ServicioHijo.class);

    hijosControlador = new HijosControlador(servicioHijoMock);

    sessionMock = mock(HttpSession.class);
    usuarioMock = mock(Usuario.class);
  }

  @Test
  public void vistaHijosSinSesionDeberiaRedirigirAlLogin() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(null);
    ModelAndView mv = hijosControlador.irAvistaHijos(sessionMock);
    assertThat(mv.getViewName(), equalToIgnoringCase("redirect:/login"));
  }

  @Test
  public void misHijosDebeMostrarLosHijosDelUsuario() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

    when(usuarioMock.getId()).thenReturn(1L);
    //simulo un hijo
    List<Hijo> hijoMock = List.of(Mockito.mock(Hijo.class));
    when(servicioHijoMock.obtenerHijosPorUsuario(1L)).thenReturn(hijoMock);

    ModelAndView modelAndView = hijosControlador.irAvistaHijos(sessionMock);

    List<Hijo> hijosObtenidos = (List<Hijo>) modelAndView.getModel().get("hijos");

    assertThat(hijosObtenidos.size(), equalTo(1));
  }

  @Test
  public void siNoTieneHijosDebeMostrarUnMensaje() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

    when(usuarioMock.getHijos()).thenReturn(null); //cuando llamo a getHijos le pido que retorne null

    ModelAndView modelAndView = hijosControlador.irAvistaHijos(sessionMock);

    assertThat(
      (String) modelAndView.getModel().get("mensajeError"),
      equalToIgnoringCase("Aún no tenés hijos registrados")
    );
  }

  @Test
  public void vistaHijosDebeMostrarLaInfoDeLosHijos() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

    Hijo hijoMock1 = Mockito.mock(Hijo.class);
    when(hijoMock1.getNombre()).thenReturn("Santiago");

    Hijo hijoMock2 = Mockito.mock(Hijo.class);
    when(hijoMock2.getNombre()).thenReturn("Romina");

    List<Hijo> hijosSimulados = List.of(hijoMock1, hijoMock2);

    when(servicioHijoMock.obtenerHijosPorUsuario(usuarioMock.getId())).thenReturn(hijosSimulados);

    ModelAndView modelAndView = hijosControlador.irAvistaHijos(sessionMock);

    List<Hijo> hijosObtenidos = (List<Hijo>) modelAndView.getModel().get("hijos");

    assertThat(hijosObtenidos.get(0).getNombre(), equalToIgnoringCase("Santiago"));
    assertThat(hijosObtenidos.get(1).getNombre(), equalToIgnoringCase("Romina"));
  }

  @Test
  public void guardarHijoDeberiaLlamarAlServicioYRecargarVistaHijos() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    Hijo hijoMock = mock(Hijo.class);
    ModelAndView modelAndView = hijosControlador.guardarHijos(hijoMock, "4", "D", sessionMock);

    verify(servicioHijoMock, times(1)).guardarHijo(hijoMock, usuarioMock);
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/vistaHijos"));
  }

  @Test
  public void guardarHijosSinSesionDeberiaRedirigirAlLogin() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(null);
    Hijo hijoMock = mock(Hijo.class);

    ModelAndView mv = hijosControlador.guardarHijos(hijoMock, "4", "D", sessionMock);

    assertThat(mv.getViewName(), equalToIgnoringCase("redirect:/login"));
  }

  @Test
  public void guardarHijoQueYaExisteDeberiaRedirigirAVistaHijosConError() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    Hijo hijoMock = mock(Hijo.class);

    doThrow(HijoExistenteException.class).when(servicioHijoMock).guardarHijo(hijoMock, usuarioMock);

    ModelAndView modelAndView = hijosControlador.guardarHijos(hijoMock, "4", "D", sessionMock);

    assertThat(modelAndView.getViewName(), equalToIgnoringCase("vistaHijos"));
    assertThat(
      modelAndView.getModel().get("error").toString(),
      equalToIgnoringCase("El hijo ya se encuentra registrado")
    );
  }

  @Test
  public void guardarHijoDeberiaGuardarElCursoCorrectamente() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    Hijo hijoMock = mock(Hijo.class);
    when(hijoMock.getCurso()).thenReturn(Curso.CUARTO_D);

    ModelAndView mv = hijosControlador.guardarHijos(hijoMock, "4", "D", sessionMock);

    verify(servicioHijoMock, times(1)).guardarHijo(hijoMock, usuarioMock);
    assertThat(mv.getViewName(), equalToIgnoringCase("redirect:/vistaHijos"));
  }

  @Test
  public void guardarHijoDeberiaSetearElCursoAntesDeGuardarlo() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    Hijo hijoMock = mock(Hijo.class);

    hijosControlador.guardarHijos(hijoMock, "3", "B", sessionMock);

    verify(hijoMock, times(1)).setCurso(Curso.TERCERO_B);
  }
}
