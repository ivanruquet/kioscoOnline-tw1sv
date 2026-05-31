package com.tallerwebi.dominio.excepcion;

public class UsuarioExistente extends Exception {

  /* Identificador para la serialización de la clase, requerido por PMD en excepciones */
  private static final long serialVersionUID = 1L;

  public UsuarioExistente() {
    super("El usuario ya se encuentra registrado en el sistema");
  }
}
