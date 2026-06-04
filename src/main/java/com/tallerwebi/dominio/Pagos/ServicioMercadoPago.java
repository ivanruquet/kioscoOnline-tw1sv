package com.tallerwebi.dominio.Pagos;

import com.tallerwebi.dominio.Pedidos.Pedido;
import java.util.List;

public interface ServicioMercadoPago {
  String crearPreferenciaDePago(List<Pedido> pedidos);
}
//preferencia de pago, se le llama a la sesion de pago de mercadopago, como si fuera un checkout
