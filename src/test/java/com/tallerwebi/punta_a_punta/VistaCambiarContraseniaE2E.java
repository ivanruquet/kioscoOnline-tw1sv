package com.tallerwebi.punta_a_punta;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaCambiarContrasenia;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VistaCambiarContraseniaE2E {

  static Playwright playwright;
  static Browser browser;
  BrowserContext context;
  VistaLogin vistaLogin;
  VistaCambiarContrasenia vistaCambiarContrasenia;

  @BeforeAll
  static void abrirNavegador() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch();
    //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(500));
  }

  @AfterAll
  static void cerrarNavegador() {
    playwright.close();
  }

  @BeforeEach
  void crearContextoYPagina() {
    ReiniciarDB.limpiarBaseDeDatos();

    context = browser.newContext();
    Page page = context.newPage();
    vistaLogin = new VistaLogin(page);
  }

  @AfterEach
  void cerrarContexto() {
    context.close();
  }

  @Test
  void alClickearOlvidasteTuContraseniaDeberiaNavegarALaVistaDeCambiarContrasenia()
    throws MalformedURLException {
    dadoQueElUsuarioEstaEnLaVistaDeLogin();
    cuandoElUsuarioClickeaOlvidasteContrasenia();
    entoncesDeberiaEstarEnLaVistaDeCambiarContrasenia();
  }

  @Test
  void dadoUnEmailRegistradoDeberiaPoderCambiarLaContraseniaExitosamente() {
    dadoQueElUsuarioEstaEnLaVistaDeLogin();
    cuandoElUsuarioClickeaOlvidasteContrasenia(); // llega a cambiarContrasenia (paso 1)

    dadoQueElUsuarioIngresaSuEmail("test@unlam.edu.ar");
    cuandoElUsuarioEnviaElCodigo();
    vistaCambiarContrasenia.esperarPaso2(); // ← acá sí
    // avanza al paso 2
    entoncesDeberiaVerElPaso2();

    dadoQueElUsuarioIngresaElCodigo("1234");
    cuandoElUsuarioVerificaElCodigo(); // avanza al paso 3
    entoncesDeberiaVerElPaso3();

    dadoQueElUsuarioIngresaLaNuevaClave("nuevaClave123");
    cuandoElUsuarioCambiaLaClave(); // avanza al paso 4
    vistaCambiarContrasenia.esperarPaso4();

    entoncesDeberiaVerElMensajeDeExito();
  }

  @Test
  void dadoUnEmailNoRegistradoDeberiaQuedarseEnElPaso1() {
    dadoQueElUsuarioEstaEnLaVistaDeLogin();
    cuandoElUsuarioClickeaOlvidasteContrasenia();

    // Aceptamos el alert() que lanza el JS cuando el email no existe
    context.pages().get(0).onDialog(dialog -> dialog.accept());

    dadoQueElUsuarioIngresaSuEmail("noexiste@unlam.edu.ar");
    cuandoElUsuarioEnviaElCodigo();

    entoncesDeberiaVerElPaso1(); // no avanzó, sigue en paso 1
  }

  @Test
  void dadoUnCodigoIncorrectoDeberiaQuedarseEnElPaso2() {
    dadoQueElUsuarioEstaEnLaVistaDeLogin();
    cuandoElUsuarioClickeaOlvidasteContrasenia();

    dadoQueElUsuarioIngresaSuEmail("test@unlam.edu.ar");
    cuandoElUsuarioEnviaElCodigo();
    vistaCambiarContrasenia.esperarPaso2();

    entoncesDeberiaVerElPaso2();

    // Aceptamos el alert() que lanza el JS cuando el código es incorrecto
    context.pages().get(0).onDialog(dialog -> dialog.accept());

    dadoQueElUsuarioIngresaElCodigo("0000");
    cuandoElUsuarioVerificaElCodigo();

    entoncesDeberiaVerElPaso2();
    // no avanzó, sigue en paso 2
  }

  @Test
  void luegoDeCambiarLaContraseniaDeberiaPoderVolverAlLogin() throws MalformedURLException {
    dadoQueElUsuarioEstaEnLaVistaDeLogin();
    cuandoElUsuarioClickeaOlvidasteContrasenia();

    dadoQueElUsuarioIngresaSuEmail("test@unlam.edu.ar");
    cuandoElUsuarioEnviaElCodigo();
    vistaCambiarContrasenia.esperarPaso2();

    dadoQueElUsuarioIngresaElCodigo("1234");
    cuandoElUsuarioVerificaElCodigo();
    dadoQueElUsuarioIngresaLaNuevaClave("nuevaClave123");
    cuandoElUsuarioCambiaLaClave();
    vistaCambiarContrasenia.esperarPaso4(); // ← agregá esto

    entoncesDeberiaVerElMensajeDeExito();

    cuandoElUsuarioClickeaVolverAlLogin();
    entoncesDeberiaEstarEnLaVistaDeLogin();
  }

  //METODOS AUXILIARES!

  private void dadoQueElUsuarioEstaEnLaVistaDeLogin() {
    // No hace falta navegar: el @BeforeEach ya arranca en el login
  }

  private void dadoQueElUsuarioIngresaSuEmail(String email) {
    vistaCambiarContrasenia.escribirEmail(email);
  }

  private void dadoQueElUsuarioIngresaElCodigo(String codigo) {
    vistaCambiarContrasenia.escribirCodigo(codigo);
  }

  private void dadoQueElUsuarioIngresaLaNuevaClave(String clave) {
    vistaCambiarContrasenia.escribirNuevaClave(clave);
  }

  // ACCIONES DEL USUARIO!!

  private void cuandoElUsuarioClickeaOlvidasteContrasenia() {
    vistaLogin.darClickEnOlvidasteContrasenia();
    vistaCambiarContrasenia = new VistaCambiarContrasenia(context.pages().get(0));
  }

  private void cuandoElUsuarioEnviaElCodigo() {
    vistaCambiarContrasenia.darClickEnEnviarCodigo();
  }

  private void cuandoElUsuarioVerificaElCodigo() {
    vistaCambiarContrasenia.darClickEnVerificarCodigo();
  }

  private void cuandoElUsuarioCambiaLaClave() {
    vistaCambiarContrasenia.darClickEnCambiarClave();
  }

  private void cuandoElUsuarioClickeaVolverAlLogin() {
    vistaCambiarContrasenia.darClickEnVolverAlLogin();
  }

  //VERIFICACIN, QUE SE ESPERA VER

  private void entoncesDeberiaEstarEnLaVistaDeCambiarContrasenia() throws MalformedURLException {
    URL url = vistaCambiarContrasenia.obtenerURLActual();
    assertThat(
      url.getPath(),
      matchesPattern("^/spring/cambiarContrasenia(?:;jsessionid=[^/\\s]+)?$")
    );
  }

  private void entoncesDeberiaVerElPaso1() {
    assertThat(vistaCambiarContrasenia.estaPaso1Visible(), is(true));
    assertThat(vistaCambiarContrasenia.estaPaso2Visible(), is(false));
  }

  private void entoncesDeberiaVerElPaso2() {
    assertThat(vistaCambiarContrasenia.estaPaso2Visible(), is(true));
  }

  private void entoncesDeberiaVerElPaso3() {
    assertThat(vistaCambiarContrasenia.estaPaso3Visible(), is(true));
  }

  private void entoncesDeberiaVerElMensajeDeExito() {
    assertThat(vistaCambiarContrasenia.estaPaso4Visible(), is(true));
    assertThat(
      vistaCambiarContrasenia.obtenerTextoExito(),
      equalToIgnoringCase("Contraseña cambiada exitosamente")
    );
  }

  private void entoncesDeberiaEstarEnLaVistaDeLogin() throws MalformedURLException {
    URL url = vistaLogin.obtenerURLActual();
    assertThat(url.getPath(), matchesPattern("^/spring/login(?:;jsessionid=[^/\\s]+)?$"));
  }
}
