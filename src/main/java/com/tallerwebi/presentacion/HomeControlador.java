package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Productos.ServicioProducto;
import com.tallerwebi.dominio.Usuario;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeControlador {

  private ServicioProducto servicioProducto;

  @Autowired
  public HomeControlador(ServicioProducto servicioProducto) {
    this.servicioProducto = servicioProducto;
  }

  @RequestMapping(path = "/home", method = RequestMethod.GET)
  public ModelAndView irAHome(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");
    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }

    ModelAndView mv = new ModelAndView();

    mv.addObject("usuario", usuario);

    List<Producto> productos = this.servicioProducto.obtenerListadoProductos();
    mv.addObject("productos", productos);

    mv.setViewName("home");

    return mv;
  }

  public ModelAndView mostrarDatosUsuario(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usuario", usuario);

    mv.setViewName("home");
    return mv;
  }

  public ModelAndView mostrarListadoProductos() {
    ModelAndView mv = new ModelAndView();

    // Traemos la lista del servicio y la metemos en el modelo
    List<Producto> productos = this.servicioProducto.obtenerListadoProductos();
    mv.addObject("productos", productos);
    mv.setViewName("home");

    return mv;
  }
}
