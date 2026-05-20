package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carrito.ItemCarrito;
import com.tallerwebi.dominio.Carrito.ServicioCarrito;
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
  private static final String PRODUCTO_ID = "productoId";

  @Autowired
  public ControladorCarrito(ServicioCarrito servicioCarrito) {
    this.servicioCarrito = servicioCarrito;
  }

  @RequestMapping(path = "/carrito/agregar", method = RequestMethod.POST)
  public String agregarProducto(
    @RequestParam(PRODUCTO_ID) Long productoId,
    HttpSession session,
    ModelMap model
  ) {
    List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute(CARRITO);

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
    List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute(CARRITO);

    if (carrito == null) {
      carrito = new ArrayList<>();
    }

    Double total = servicioCarrito.calcularTotal(carrito);

    ModelMap model = new ModelMap();
    model.put(CARRITO, carrito);
    model.put("total", total);

    return new ModelAndView(CARRITO, model);
  }

  @RequestMapping(path = "/carrito/eliminar", method = RequestMethod.POST)
  public String eliminarProducto(@RequestParam(PRODUCTO_ID) Long productoId, HttpSession session) {
    List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute(CARRITO);

    if (carrito == null) {
      carrito = new ArrayList<>();
    }

    carrito = servicioCarrito.eliminarProducto(productoId, carrito);

    session.setAttribute(CARRITO, carrito);

    return "redirect:/carrito";
  }

  @RequestMapping(path = "/carrito/aumentar", method = RequestMethod.POST)
  public String aumentarCantidadDeProducto(
    @RequestParam(PRODUCTO_ID) Long productoId,
    HttpSession session
  ) {
    List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute(CARRITO);

    if (carrito == null) {
      carrito = new ArrayList<>();
    }

    carrito = servicioCarrito.aumentarCantidad(productoId, carrito);

    session.setAttribute(CARRITO, carrito);

    return "redirect:/carrito";
  }

  @RequestMapping(path = "/carrito/restar", method = RequestMethod.POST)
  public String restarCantidadDeProducto(
    @RequestParam(PRODUCTO_ID) Long productoId,
    HttpSession session
  ) {
    List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute(CARRITO);

    if (carrito == null) {
      carrito = new ArrayList<>();
    }

    carrito = servicioCarrito.restarCantidad(productoId, carrito);

    session.setAttribute(CARRITO, carrito);

    return "redirect:/carrito";
  }
}
