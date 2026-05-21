package com.tallerwebi.dominio.Carrito;

import java.util.List;

public interface ServicioCarrito {
  List<ItemCarrito> agregarProducto(long id, List<ItemCarrito> carrito);

  Double calcularTotal(List<ItemCarrito> carrito);

  List<ItemCarrito> eliminarProducto(long id, List<ItemCarrito> carrito);

  List<ItemCarrito> aumentarCantidad(long id, List<ItemCarrito> carrito);

  List<ItemCarrito> restarCantidad(long id, List<ItemCarrito> carrito);
}
