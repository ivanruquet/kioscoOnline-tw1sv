package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carrito.Carrito;
import com.tallerwebi.dominio.Carrito.ItemCarrito;
import com.tallerwebi.dominio.Carrito.ServicioCarrito;
import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.ServicioHijo;
import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DistribucionControlador {

  private final ServicioCarrito servicioCarrito;
  private final ServicioHijo servicioHijo;
  private static final String USUARIO_SESSION = "USUARIO";

  @Autowired
  public DistribucionControlador(ServicioCarrito servicioCarrito, ServicioHijo servicioHijo) {
    this.servicioCarrito = servicioCarrito;
    this.servicioHijo = servicioHijo;
  }

  @RequestMapping(path = "/distribucion", method = RequestMethod.GET)
  public ModelAndView verDistribucion(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESSION);

    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }

    Carrito carrito = servicioCarrito.obtenerOCrearCarrito(usuario.getId());
    List<Hijo> hijos = servicioHijo.obtenerHijosPorUsuario(usuario.getId());

    List<Producto> productos = carrito
      .getItems()
      .stream()
      .map(ItemCarrito::getProducto)
      .collect(Collectors.toList());

    ModelMap model = new ModelMap();
    model.put("productos", productos);
    model.put("hijos", hijos);
    model.put("usuario", usuario);

    return new ModelAndView("carritoDistribucion", model);
  }
}
