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
import com.tallerwebi.dominio.excepcion.HijoNoEncontradoException;
import com.tallerwebi.presentacion.HijosControlador;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class HijosControladorTest {

  private Usuario usuarioMock;
  private HijosControlador hijosControlador;
  private ServicioHijo servicioHijoMock;
  private HttpSession sessionMock;
  private DatosEditarHijoDTO datosHijoMock;
  private BindingResult bindingResultMock;
  private RedirectAttributes redirectAttributesMock;
  private MultipartFile fotoMock;

  @BeforeEach
  public void init() {
    servicioHijoMock = mock(ServicioHijo.class);
    hijosControlador = new HijosControlador(servicioHijoMock);
    sessionMock = mock(HttpSession.class);
    usuarioMock = mock(Usuario.class);
    datosHijoMock = mock(DatosEditarHijoDTO.class);
    bindingResultMock = mock(BindingResult.class);
    redirectAttributesMock = mock(RedirectAttributes.class);
    fotoMock = mock(MultipartFile.class);
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
    ModelAndView modelAndView = hijosControlador.guardarHijos(
      hijoMock,
      "CUARTO",
      "D",
      fotoMock,
      sessionMock,
      redirectAttributesMock
    );
    verify(servicioHijoMock, times(1)).guardarHijo(hijoMock, fotoMock, usuarioMock);
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/vistaHijos"));
  }

  @Test
  public void guardarHijosSinSesionDeberiaRedirigirAlLogin() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(null);
    Hijo hijoMock = mock(Hijo.class);

    ModelAndView mv = hijosControlador.guardarHijos(
      hijoMock,
      "CUARTO",
      "D",
      fotoMock,
      sessionMock,
      redirectAttributesMock
    );
    assertThat(mv.getViewName(), equalToIgnoringCase("redirect:/login"));
  }

  @Test
  public void guardarHijoQueYaExisteDeberiaRedirigirAVistaHijosConError() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    Hijo hijoMock = mock(Hijo.class);

    doThrow(HijoExistenteException.class)
      .when(servicioHijoMock)
      .guardarHijo(hijoMock, fotoMock, usuarioMock);

    ModelAndView modelAndView = hijosControlador.guardarHijos(
      hijoMock,
      "CUARTO",
      "D",
      fotoMock,
      sessionMock,
      redirectAttributesMock
    );
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

    ModelAndView mv = hijosControlador.guardarHijos(
      hijoMock,
      "CUARTO",
      "D",
      fotoMock,
      sessionMock,
      redirectAttributesMock
    );
    verify(servicioHijoMock, times(1)).guardarHijo(hijoMock, fotoMock, usuarioMock);
    assertThat(mv.getViewName(), equalToIgnoringCase("redirect:/vistaHijos"));
  }

  @Test
  public void guardarHijoDeberiaSetearElCursoAntesDeGuardarlo() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    Hijo hijoReal = new Hijo();
    hijosControlador.guardarHijos(
      hijoReal,
      "TERCERO",
      "D",
      fotoMock,
      sessionMock,
      redirectAttributesMock
    );
    assertThat(hijoReal.getCurso(), equalTo(Curso.TERCERO_D));
  }

  @Test
  public void editarHijoDeberiaLlamarAlServicioYRecargarLaVistaHijos() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    when(bindingResultMock.hasErrors()).thenReturn(false); // Simulamos validación exitosa

    when(datosHijoMock.getIdHijo()).thenReturn(1L);
    when(datosHijoMock.getAnio()).thenReturn("CUARTO");
    when(datosHijoMock.getDivision()).thenReturn("D");
    MultipartFile fotoMock = mock(MultipartFile.class);
    when(datosHijoMock.getFotoPerfilH()).thenReturn(fotoMock);

    ModelAndView mav = hijosControlador.editarHijo(
      datosHijoMock,
      bindingResultMock,
      sessionMock,
      redirectAttributesMock
    );
    verify(servicioHijoMock, times(1))
      .editarHijo(eq(1L), any(Hijo.class), eq(fotoMock), eq(usuarioMock));

    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/vistaHijos"));
  }

  @Test
  public void editarHijoQueNoExisteDeberiaRedirigirAVistaHijosConError() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    when(bindingResultMock.hasErrors()).thenReturn(false); // Simulamos validación exitosa

    when(datosHijoMock.getIdHijo()).thenReturn(1L);
    when(datosHijoMock.getAnio()).thenReturn("CUARTO");
    when(datosHijoMock.getDivision()).thenReturn("D");
    MultipartFile fotoMock = mock(MultipartFile.class);
    when(datosHijoMock.getFotoPerfilH()).thenReturn(fotoMock);

    doThrow(HijoNoEncontradoException.class)
      .when(servicioHijoMock)
      .editarHijo(eq(1L), any(Hijo.class), eq(fotoMock), eq(usuarioMock));

    ModelAndView mav = hijosControlador.editarHijo(
      datosHijoMock,
      bindingResultMock,
      sessionMock,
      redirectAttributesMock
    );
    assertThat(mav.getViewName(), equalToIgnoringCase("vistaHijos"));
    assertThat(
      mav.getModel().get("error").toString(),
      equalToIgnoringCase("El hijo no existe o no pertenece al usuario")
    );
  }

  @Test
  public void seDebePoderCambiarLaFotoDelHijo() {
    // 1. Preparación del entorno (Sesión y Mock del archivo de imagen)
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    when(bindingResultMock.hasErrors()).thenReturn(false); // Simulamos validación exitosa
    MultipartFile fotoMock = Mockito.mock(MultipartFile.class);
    when(fotoMock.isEmpty()).thenReturn(false); // Simulamos que el usuario SÍ subió un archivo

    // 2. Configuración de los comportamientos del DTO (Stubs con 'when')
    when(datosHijoMock.getIdHijo()).thenReturn(1L);
    when(datosHijoMock.getAnio()).thenReturn("CUARTO");
    when(datosHijoMock.getDivision()).thenReturn("D");
    when(datosHijoMock.getFotoPerfilH()).thenReturn(fotoMock);

    // 3. Ejecución del método del controlador
    // Pasamos el bindingResultMock inmediatamente después del DTO para cumplir la firma
    ModelAndView mav = hijosControlador.editarHijo(
      datosHijoMock,
      bindingResultMock,
      sessionMock,
      redirectAttributesMock
    );
    // 4. Verificaciones (Asserts y Verifies)
    // Verificamos que el servicio recibió exactamente el mock de la foto para procesarlo
    verify(servicioHijoMock, times(1))
      .editarHijo(eq(1L), any(Hijo.class), eq(fotoMock), eq(usuarioMock));

    // Verificamos que al salir todo bien, nos redirija correctamente
    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/vistaHijos"));
  }
}
