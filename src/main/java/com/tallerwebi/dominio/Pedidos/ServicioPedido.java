package com.tallerwebi.dominio.Pedidos;

import com.tallerwebi.dominio.Usuario.Usuario;
import com.tallerwebi.presentacion.DistribucionCarrito.ItemDistribucionDTO;
import java.util.List;

public interface ServicioPedido {
  void crearPedido(Long hijoId, List<ItemDistribucionDTO> items, Usuario usuario);
  List<Pedido> obtenerPedidosPendientesDePago(Long usuarioId);
}
