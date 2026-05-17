package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Productos.CategoriaProductos;
import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Productos.ServicioProducto;
import com.tallerwebi.dominio.Usuario;
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
public class HomeControlador {

  private static final String VISTA_HOME = "home";
  private final ServicioProducto servicioProducto;

  @Autowired
  public HomeControlador(ServicioProducto servicioProducto) {
    this.servicioProducto = servicioProducto;
  }

  @RequestMapping(path = "/home", method = RequestMethod.GET)
  public ModelAndView irAHome(
    HttpSession session,
    @RequestParam(value = "categoria", required = false) String categoria,
    @RequestParam(value = "busqueda", required = false) String busqueda
  ) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");
    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }
    ModelMap modelo = new ModelMap();
    //LLAMO A LOS METODOS PRIVADOS REFACTORIZADOS
    this.cargarDatosUsuario(modelo, usuario);
    this.cargarCategorias(modelo);
    this.cargarProductos(modelo, categoria);
    this.cargarResultadoBusqueda(modelo, busqueda);

    return new ModelAndView(VISTA_HOME, modelo);
  }

  private void cargarDatosUsuario(ModelMap modelo, Usuario usuario) {
    modelo.put("usuario", usuario);
  }

  private void cargarCategorias(ModelMap modelo) {
    List<CategoriaProductos> categorias = this.servicioProducto.obtenerListadoCategorias();
    modelo.put("categorias", categorias);
  }

  private void cargarProductos(ModelMap modelo, String categoria) {
    List<Producto> productos;
    if (categoria != null && !categoria.isEmpty()) {
      productos = this.servicioProducto.obtenerListadoProductosFiltrado(categoria);
    } else {
      productos = this.servicioProducto.obtenerListadoProductos();
    }
    modelo.put("productos", productos);
  }

  private void cargarResultadoBusqueda(ModelMap modelo, String busqueda) {
    if (busqueda != null && !busqueda.trim().isEmpty()) {
      List<Producto> buscados = this.servicioProducto.buscarProductosPorNombre(busqueda);
      modelo.put("productosBuscados", buscados);
    }
  }

  //METODOS DE LOS TEST!!
  public ModelAndView mostrarDatosUsuario(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");
    ModelMap modelo = new ModelMap();
    modelo.put("usuario", usuario);
    return new ModelAndView(VISTA_HOME, modelo);
  }

  public ModelAndView mostrarListadoProductos() {
    ModelMap modelo = new ModelMap();
    List<Producto> productos = this.servicioProducto.obtenerListadoProductos();
    modelo.put("productos", productos);
    return new ModelAndView(VISTA_HOME, modelo);
  }

  public ModelAndView mostrarListadoProductosFiltrados(String categoriaProductos) {
    ModelMap modelo = new ModelMap();
    List<Producto> productos =
      this.servicioProducto.obtenerListadoProductosFiltrado(categoriaProductos);
    modelo.put("productos", productos);
    return new ModelAndView(VISTA_HOME, modelo);
  }

  public ModelAndView mostrarCategoriasEnNav() {
    ModelMap modelo = new ModelMap();
    List<CategoriaProductos> categoriaProductos = this.servicioProducto.obtenerListadoCategorias();
    modelo.put("categorias", categoriaProductos); // Usamos "categorias" en plural
    return new ModelAndView(VISTA_HOME, modelo);
  }

  public ModelAndView mostrarProductosBuscados(String texto) {
    ModelMap modelo = new ModelMap();
    List<Producto> productosBuscados = this.servicioProducto.buscarProductosPorNombre(texto);
    modelo.put("productosBuscados", productosBuscados);
    return new ModelAndView(VISTA_HOME, modelo);
  }
}
