package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carrito.ServicioCarrito;
import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.excepcion.ProductoNoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCarrito {

  private ServicioCarrito servicioCarrito;
  private static final String CARRITO = "carrito";

  @Autowired
  public ControladorCarrito(ServicioCarrito servicioCarrito) {
    this.servicioCarrito = servicioCarrito;
  }

  @RequestMapping(path = "/carrito/agregar", method = RequestMethod.POST)
  public String agregarProducto(
    @RequestParam("productoId") Long productoId,
    HttpSession session,
    ModelMap model
  ) {
    List<Producto> carrito = (List<Producto>) session.getAttribute(CARRITO);

    if (carrito == null) {
      carrito = new ArrayList<>();
    }

    try {
      carrito = servicioCarrito.agregarProducto(productoId, carrito);

      session.setAttribute(CARRITO, carrito);
    } catch (ProductoNoEncontradoException e) {
      model.put("error", e.getMessage());
    }

    return "redirect:/home";
  }

  @RequestMapping(path = "/carrito", method = RequestMethod.GET)
  public ModelAndView verCarrito(HttpSession session) {
    List<Producto> carrito = (List<Producto>) session.getAttribute(CARRITO);

    if (carrito == null) {
      carrito = new ArrayList<>();
    }

    ModelMap model = new ModelMap();
    model.put(CARRITO, carrito);

    return new ModelAndView(CARRITO, model);
  }
}
