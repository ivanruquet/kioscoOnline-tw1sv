package com.tallerwebi.presentacion.Pagos;

import com.tallerwebi.dominio.Carrito.Carrito;
import com.tallerwebi.dominio.Carrito.ServicioCarrito;
import com.tallerwebi.dominio.Pagos.ServicioMercadoPago;
import com.tallerwebi.dominio.Pedidos.Pedido;
import com.tallerwebi.dominio.Pedidos.ServicioPedido;
import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMercadoPago {

  private final ServicioMercadoPago servicioMercadoPago;
  private final ServicioCarrito servicioCarrito;
  private final ServicioPedido servicioPedido;

  @Autowired
  public ControladorMercadoPago(
    ServicioMercadoPago servicioMercadoPago,
    ServicioCarrito servicioCarrito,
    ServicioPedido servicioPedido
  ) {
    this.servicioMercadoPago = servicioMercadoPago;
    this.servicioCarrito = servicioCarrito;
    this.servicioPedido = servicioPedido;
  }

  @RequestMapping(path = "/pagar", method = RequestMethod.GET)
  public ModelAndView pagar(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");

    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }

    List<Pedido> pedidos = servicioPedido.obtenerPedidosPendientesDePago(usuario.getId());

    if (pedidos.isEmpty()) {
      ModelMap model = new ModelMap();
      model.put("error", "No hay pedidos para pagar");
      return new ModelAndView("redirect:/carrito", model);
    }

    String urlPago = servicioMercadoPago.crearPreferenciaDePago(pedidos);

    if (urlPago == null) {
      ModelMap model = new ModelMap();
      model.put("error", "No se pudo conectar con Mercado Pago. Intente más tarde.");
      return new ModelAndView("redirect:/carrito", model);
    }

    return new ModelAndView("redirect:" + urlPago);
  }

  @RequestMapping(path = "/pago-exitoso", method = RequestMethod.GET)
  public ModelAndView mostrarPagoExitoso(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");

    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }

    Carrito carrito = servicioCarrito.obtenerOCrearCarrito(usuario.getId());

    if (carrito == null || carrito.getItems().isEmpty()) {
      ModelMap model = new ModelMap();
      model.put("error", "Debés agregar items y realizar una compra primero.");
      return new ModelAndView("redirect:/carrito", model);
    }

    ModelMap model = new ModelMap();
    model.put("itemsComprados", carrito.getItems());
    return new ModelAndView("pago-exitoso", model);
  }
}
