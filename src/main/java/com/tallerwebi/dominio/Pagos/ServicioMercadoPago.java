package com.tallerwebi.dominio.Pagos;

import com.tallerwebi.dominio.Carrito.Carrito;

public interface ServicioMercadoPago {
  String crearPreferenciaDePago(Carrito carrito);
}
//preferencia de pago, se le llama a la sesion de pago de mercadopago, como si fuera un checkout