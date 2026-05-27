package com.tallerwebi.dominio.Carrito;

public interface ServicioCarrito {
  void agregarProducto(long productoId, Carrito carrito);

  Double calcularTotal(Carrito carrito);

  void eliminarProducto(long productoId, Carrito carrito);

  void aumentarCantidad(long productoId, Carrito carrito);

  void disminuirCantidad(long productoId, Carrito carrito);
}
