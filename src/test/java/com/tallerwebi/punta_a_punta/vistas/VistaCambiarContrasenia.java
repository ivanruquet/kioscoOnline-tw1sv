package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaCambiarContrasenia extends VistaWeb {

  public VistaCambiarContrasenia(Page page) {
    super(page);
    page.navigate("localhost:8080/spring/cambiarContrasenia");
  }

  //paso 1--> ya estando en la vista de cambiarContrasenia
  //el usuario debe escribir su email y hacer click en enviar codigo!
  public void escribirEmail(String email) {
    this.escribirEnElElemento("#email-Rec", email);
  }

  public void darClickEnEnviarCodigo() {
    this.darClickEnElElemento("#btn-enviarCod");
  }

  //con mail correcto se pasa al siguiente paso
  //paso 2--> el usuario debe ingresar el codigo que se le envia al mail, x el momento esta harcodeado
  //al escribirlo debe hacer click en el boton de verificar
  public void escribirCodigo(String codigo) {
    this.escribirEnElElemento("#codigo", codigo);
  }

  public void darClickEnVerificarCodigo() {
    this.darClickEnElElemento("#btn-verificarCod");
  } //si el codigo es correcto pasamos al paso 3

  //paso 3, se le pide al usuario que ingrese una nueva contraseña..
  public void escribirNuevaClave(String clave) {
    this.escribirEnElElemento("#nuevaClave", clave);
  }

  public void darClickEnCambiarClave() {
    this.darClickEnElElemento("#btn-cambiarClave");
  } //una vez que la cambia esta se actualiza en la bd

  //paso 4-->  luego debe volver al login para iniciar sesion con su nueva clave
  public void darClickEnVolverAlLogin() {
    this.darClickEnElElemento("#btn-volverAlLogin");
  }

  public void esperarPaso2() {
    page.locator("#paso2").waitFor();
  }

  // consultas para saber si el paso siguiente esta visible
  public Boolean estaPaso1Visible() {
    return page.locator("#paso1").isVisible();
  }

  public boolean estaPaso2Visible() {
    return page.locator("#paso2").isVisible();
  }

  public boolean estaPaso3Visible() {
    return page.locator("#paso3").isVisible();
  }

  public boolean estaPaso4Visible() {
    return page.locator("#paso4").isVisible();
  }

  public String obtenerTextoExito() {
    return this.obtenerTextoDelElemento("#paso4 h2");
  }

  public void esperarPaso4() {
    page.locator("#paso4").waitFor();
  }
}
