package com.tallerwebi.dominio.excepcion;

public class ProductoNoEncontradoException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ProductoNoEncontradoException(String mensaje) {
    super(mensaje);
  } //le agrego el mensaje para poder personalizarlo tanto para la busqueda como para las categorias vacias)
}
