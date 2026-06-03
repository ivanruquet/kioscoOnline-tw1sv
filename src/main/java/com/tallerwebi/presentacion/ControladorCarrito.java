package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carrito.Carrito;
import com.tallerwebi.dominio.Carrito.ServicioCarrito;
import com.tallerwebi.dominio.Usuario.Usuario;
import com.tallerwebi.dominio.excepcion.ProductoNoEncontradoException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCarrito {

  private final ServicioCarrito servicioCarrito;
  private static final String USUARIO = "USUARIO";
  private static final String CARRITO = "carrito";
  private static final String PRODUCTO_ID = "productoId";

  @Autowired
  public ControladorCarrito(ServicioCarrito servicioCarrito) {
    this.servicioCarrito = servicioCarrito;
  }

  @RequestMapping(path = "/carrito/agregar", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<String> agregarProducto(
    @RequestParam(PRODUCTO_ID) Long productoId,
    HttpSession session
  ) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO);

    try {
      servicioCarrito.agregarProducto(productoId, usuario.getId());
      return ResponseEntity.ok("ok");
    } catch (ProductoNoEncontradoException e) {
      return ResponseEntity.status(400).body(e.getMessage());
    }
  }

  @RequestMapping(path = "/carrito", method = RequestMethod.GET)
  public ModelAndView verCarrito(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO);

    Carrito carrito = servicioCarrito.obtenerOCrearCarrito(usuario.getId());

    Double total = servicioCarrito.calcularTotal(usuario.getId());

    ModelMap model = new ModelMap();
    model.put(CARRITO, carrito);
    model.put("total", total);

    return new ModelAndView(CARRITO, model);
  }

  @RequestMapping(path = "/carrito/eliminar", method = RequestMethod.POST)
  public String eliminarProducto(@RequestParam(PRODUCTO_ID) Long productoId, HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO);

    servicioCarrito.eliminarProducto(productoId, usuario.getId());

    return "redirect:/carrito";
  }

  @RequestMapping(path = "/carrito/aumentar", method = RequestMethod.POST)
  public String aumentarCantidadDeProducto(
    @RequestParam(PRODUCTO_ID) Long productoId,
    HttpSession session
  ) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO);

    servicioCarrito.aumentarCantidad(productoId, usuario.getId());

    return "redirect:/carrito";
  }

  @RequestMapping(path = "/carrito/disminuir", method = RequestMethod.POST)
  public String restarCantidadDeProducto(
    @RequestParam(PRODUCTO_ID) Long productoId,
    HttpSession session
  ) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO);

    servicioCarrito.disminuirCantidad(productoId, usuario.getId());

    return "redirect:/carrito";
  }
}
