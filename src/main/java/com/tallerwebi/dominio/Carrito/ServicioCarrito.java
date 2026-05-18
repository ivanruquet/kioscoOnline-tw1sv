package com.tallerwebi.dominio.Carrito;

import com.tallerwebi.dominio.Productos.Producto;
import java.util.List;

public interface ServicioCarrito {
  List<Producto> agregarProducto(long id, List<Producto> carrito);

  Double calcularTotal(List<Producto> carrito);

  List<Producto> eliminarProducto(long id, List<Producto> carrito);
}
