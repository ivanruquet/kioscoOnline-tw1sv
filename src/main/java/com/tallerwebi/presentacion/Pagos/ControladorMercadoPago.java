package com.tallerwebi.presentacion.Pagos;

import com.tallerwebi.dominio.Carrito.ItemCarrito;
import com.tallerwebi.dominio.Pagos.ServicioMercadoPago;
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

  @Autowired
  public ControladorMercadoPago(ServicioMercadoPago servicioMercadoPago) {
    this.servicioMercadoPago = servicioMercadoPago;
  }

  @RequestMapping(path = "/pagar", method = RequestMethod.GET)
  public ModelAndView pagar(HttpSession session) {
    List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");

    if (carrito == null || carrito.isEmpty()) {
      ModelMap model = new ModelMap();
      model.put("error", "El carrito no puede estar vacío");
      return new ModelAndView("redirect:/carrito", model);
    }

    String urlPago = servicioMercadoPago.crearPreferenciaDePago(carrito);

    // Si la API falla, redirigimos controladamente en lugar de colgar el servidor
    if (urlPago == null) {
      ModelMap model = new ModelMap();
      model.put("error", "No se pudo conectar con Mercado Pago. Intente más tarde.");
      return new ModelAndView("redirect:/carrito", model);
    }

    return new ModelAndView("redirect:" + urlPago);
  }

  @RequestMapping(path = "/pago-exitoso", method = RequestMethod.GET)
  public ModelAndView mostrarPagoExitoso(HttpSession session) {
    List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");

    ModelMap model = new ModelMap();

    if (carrito != null && !carrito.isEmpty()) {
      // Le pasamos los ítems a la vista antes de vaciarlo
      model.put("itemsComprados", carrito);
      // Vaciamos el carrito de la sesión porque ya fue pagado
      session.setAttribute("carrito", null);
    }

    return new ModelAndView("pago-exitoso", model);
  }
}
