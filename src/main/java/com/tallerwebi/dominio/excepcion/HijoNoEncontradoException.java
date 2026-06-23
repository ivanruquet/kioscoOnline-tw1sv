package com.tallerwebi.dominio.excepcion;

public class HijoNoEncontradoException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public HijoNoEncontradoException() {
    super("El hijo no existe o no pertenece al usuario");
  }
}
