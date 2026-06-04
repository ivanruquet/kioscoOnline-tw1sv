package com.tallerwebi.dominio.Carrito;

public interface ServicioCarrito {
  Carrito obtenerOCrearCarrito(Long usuarioId);

  void agregarProducto(long productoId, long usuarioId);

  Double calcularTotal(long usuarioId);

  void eliminarProducto(long productoId, long usuarioId);

  void aumentarCantidad(long productoId, long usuarioId);

  void disminuirCantidad(long productoId, long usuarioId);
  void vaciarCarrito(long usuarioId);
}
