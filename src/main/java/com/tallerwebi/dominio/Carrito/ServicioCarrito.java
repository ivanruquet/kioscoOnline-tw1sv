package com.tallerwebi.dominio.Carrito;

import com.tallerwebi.dominio.Productos.Producto;
import java.util.List;

public interface ServicioCarrito {
  List<Producto> agregarProducto(long id, List<Producto> carrito);
}
