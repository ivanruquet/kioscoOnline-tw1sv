package com.tallerwebi.dominio.excepcion;

public class NoSePudoGuardarInformacionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NoSePudoGuardarInformacionException(String mensaje) {
    super(mensaje);
  }

  public NoSePudoGuardarInformacionException(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }
}
