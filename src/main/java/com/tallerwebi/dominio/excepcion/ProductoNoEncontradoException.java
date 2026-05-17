package com.tallerwebi.dominio.excepcion;

public class ProductoNoEncontradoException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ProductoNoEncontradoException() {
    super("El producto no fue encontrado");
  }
}
