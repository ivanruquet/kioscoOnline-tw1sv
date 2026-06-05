package com.tallerwebi.punta_a_punta;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaHijos;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import org.junit.jupiter.api.*;

public class VistaHijosE2E {

  static Playwright playwright;
  static Browser browser;

  BrowserContext context;

  VistaLogin vistaLogin;
  VistaHijos vistaHijos;

  @BeforeAll
  static void abrirNavegador() {
    playwright = Playwright.create();
    //browser = playwright.chromium().launch();
    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(500));

  }

  @AfterAll
  static void cerrarNavegador() {
    playwright.close();
  }

  @BeforeEach
  void init() {
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
  void deberiaCrearUnHijoCorrectamente() {
    dadoQueElUsuarioIniciaSesion();

    dadoQueElUsuarioSeEncuentraEnLaVistaDeHijos();

    cuandoCompletaElFormularioDelHijo();

    entoncesDeberiaVerAlHijoCreado();
  }

  private void entoncesDeberiaVerAlHijoCreado() {
    assertThat(vistaHijos.obtenerNombreDelPrimerHijo(), equalToIgnoringCase("Juan"));
  }

  private void cuandoCompletaElFormularioDelHijo() {
    vistaHijos.darClickEnAgregarHijo();

    vistaHijos.escribirNombre("Juan");

    vistaHijos.escribirApellido("Perez");

    vistaHijos.escribirDni("12345678");

    vistaHijos.escribirFechaNacimiento("2018-05-10");

    vistaHijos.seleccionarAnio("1");

    vistaHijos.seleccionarDivision("A");

    vistaHijos.darClickEnGuardar();
  }

  private void dadoQueElUsuarioSeEncuentraEnLaVistaDeHijos() {
    vistaHijos = new VistaHijos(context.pages().get(0));
  }

  private void dadoQueElUsuarioIniciaSesion() {
    vistaLogin.escribirEMAIL("test@unlam.edu.ar");

    vistaLogin.escribirClave("test");

    vistaLogin.darClickEnIniciarSesion();
  }
}
