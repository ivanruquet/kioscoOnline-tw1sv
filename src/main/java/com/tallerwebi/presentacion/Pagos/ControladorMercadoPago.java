package com.tallerwebi.presentacion.Pagos;

import com.tallerwebi.dominio.Carrito.Carrito;
import com.tallerwebi.dominio.Carrito.ServicioCarrito;
import com.tallerwebi.dominio.Pagos.ServicioMercadoPago;
import com.tallerwebi.dominio.Usuario.Usuario;
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

  @Autowired
  public ControladorMercadoPago(
    ServicioMercadoPago servicioMercadoPago,
    ServicioCarrito servicioCarrito
  ) {
    this.servicioMercadoPago = servicioMercadoPago;
    this.servicioCarrito = servicioCarrito;
  }

  @RequestMapping(path = "/pagar", method = RequestMethod.GET)
  public ModelAndView pagar(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");

    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }

    Carrito carrito = servicioCarrito.obtenerOCrearCarrito(usuario.getId());

    if (carrito == null || carrito.getItems() == null || carrito.getItems().isEmpty()) {
      ModelMap model = new ModelMap();
      model.put("error", "El carrito no puede estar vacío");
      return new ModelAndView("redirect:/carrito", model);
    }

    String urlPago = servicioMercadoPago.crearPreferenciaDePago(carrito);

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

    ModelMap model = new ModelMap();

    if (usuario != null) {
      Carrito carrito = servicioCarrito.obtenerOCrearCarrito(usuario.getId());
      if (carrito != null) {
        model.put("itemsComprados", carrito.getItems());
      }
    }

    return new ModelAndView("pago-exitoso", model);
  }
}
