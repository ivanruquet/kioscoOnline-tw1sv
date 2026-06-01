package com.tallerwebi.dominio.excepcion;

public class HijoExistenteException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public HijoExistenteException() {
    super("El hijo ya se encuentra registrado");
  }
}
