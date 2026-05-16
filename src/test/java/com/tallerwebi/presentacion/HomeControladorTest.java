package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Productos.ServicioProducto;
import com.tallerwebi.dominio.Usuario;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

public class HomeControladorTest {

  private HomeControlador homeControlador;
  private Usuario usuarioMock;
  private HttpSession sessionMock;
  private Producto productoMock;
  private ServicioProducto servicioProductoMock;

  @BeforeEach
  public void init() {
    servicioProductoMock = Mockito.mock(ServicioProducto.class);
    homeControlador = new HomeControlador(servicioProductoMock);
    usuarioMock = Mockito.mock(Usuario.class);
    productoMock = Mockito.mock(Producto.class);
    sessionMock = Mockito.mock(HttpSession.class);
  }

  @Test
  public void siNoHayUsuarioLogueadoDebeVolverALogin() {
    // preparacion
    when(sessionMock.getAttribute("USUARIO")).thenReturn(null);

    // ejecucion
    ModelAndView modelAndView = homeControlador.irAHome(sessionMock);

    // validacion
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
  }

  @Test
  public void elHomeDebeMostrarNombreDeUsuario() {
    //preparacion
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

    when(usuarioMock.getNombre()).thenReturn("Rocio");

    //ejecucion
    ModelAndView modelAndView = homeControlador.mostrarDatosUsuario(sessionMock);

    //validacion

    assertThat(
      ((Usuario) modelAndView.getModel().get("usuario")).getNombre(),
      equalToIgnoringCase("Rocio")
    );
  }

  @Test
  public void elHomeDebeMostrarFotoDePerfilDelUsuario() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    when(usuarioMock.getFotoPerfil()).thenReturn("fotoUsuario.jpeg");

    ModelAndView modelAndView = homeControlador.mostrarDatosUsuario(sessionMock);

    assertThat(
      ((Usuario) modelAndView.getModel().get("usuario")).getFotoPerfil(),
      equalToIgnoringCase("fotoUsuario.jpeg")
    );
  }

  @Test
  public void seDebeVerElListadoDeProductos() {
    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
    //preparacion
    when(productoMock.getNombre()).thenReturn("Mogul");

    List<Producto> productos = Arrays.asList(productoMock);

    when(servicioProductoMock.obtenerListadoProductos()).thenReturn(productos);
    //ejecucion
    ModelAndView modelAndView = homeControlador.mostrarListadoProductos();
    //validacion
    List<Producto> productosObtenidos = (List<Producto>) modelAndView.getModel().get("productos");

    assertThat(productosObtenidos.get(0).getNombre(), equalToIgnoringCase("Mogul"));
  }
}
