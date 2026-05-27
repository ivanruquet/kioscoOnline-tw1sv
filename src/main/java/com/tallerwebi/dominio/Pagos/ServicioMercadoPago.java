package com.tallerwebi.dominio.Pagos;

import com.tallerwebi.dominio.Carrito.ItemCarrito;
import java.util.List;

public interface ServicioMercadoPago {
  String crearPreferenciaDePago(List<ItemCarrito> carrito);
}
