package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Carrito.Carrito;
import com.tallerwebi.dominio.Carrito.ItemCarrito;
import com.tallerwebi.dominio.Carrito.ServicioCarrito;
import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.ServicioHijo;
import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

public class DistribucionControladorTest {

  private HttpSession sessionMock;
  private DistribucionControlador distriControlador;
  private ServicioCarrito serviCarritomock;
  private ServicioHijo serviHijoMock;
  private Usuario usuarioMock;
  private Carrito carritoMock;
  private Producto productoMock;
  private ItemCarrito itemMock;
  private Hijo hijoMock;

  @BeforeEach
  public void init() {
    sessionMock = Mockito.mock(HttpSession.class);
    serviCarritomock = Mockito.mock(ServicioCarrito.class);
    serviHijoMock = Mockito.mock(ServicioHijo.class);
    distriControlador = new DistribucionControlador(serviCarritomock, serviHijoMock);
    usuarioMock = Mockito.mock(Usuario.class);
    carritoMock = Mockito.mock(Carrito.class);
    productoMock=Mockito.mock(Producto.class);
    itemMock=Mockito.mock(ItemCarrito.class);
    hijoMock=Mockito.mock(Hijo.class);
  }

  @Test
  public void siNoHayUsuarioLogueadoDebeVolverALogin() {
    // preparacion
    when(sessionMock.getAttribute("USUARIO")).thenReturn(null);

    // ejecucion
    ModelAndView mv = distriControlador.verDistribucion(sessionMock);

    // validacion
    assertThat(mv.getViewName(), equalToIgnoringCase("redirect:/login"));
  }

  @Test
  public void siHayUsuarioDebeMostrarLaVistaDeDistribucionDeCarrito() {

    when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

    when(carritoMock.getItems()).thenReturn(new ArrayList<>());
    when(serviCarritomock.obtenerOCrearCarrito(any())).thenReturn(carritoMock);
    when(serviHijoMock.obtenerHijosPorUsuario(any())).thenReturn(new ArrayList<>());

    ModelAndView mv = distriControlador.verDistribucion(sessionMock);
    assertThat(mv.getViewName(), equalToIgnoringCase("carritoDistribucion"));
  }
      @Test
    public void siElUsuarioAgregoProductosEstosDebenEstarEnElCarrito(){
      usuarioMock.setId(1L);
      when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

      when(productoMock.getNombre()).thenReturn("Mogul");
      when(itemMock.getProducto()).thenReturn(productoMock);

      when(carritoMock.getItems()).thenReturn(List.of(itemMock));
      when(serviCarritomock.obtenerOCrearCarrito(any())).thenReturn(carritoMock);

      when(serviHijoMock.obtenerHijosPorUsuario(any())).thenReturn(new ArrayList<>());

      ModelAndView mv = distriControlador.verDistribucion(sessionMock);

      List<Producto> productos = (List<Producto>) mv.getModel().get("productos");
      assertThat(productos.size(),org.hamcrest.Matchers.equalTo(1));
      assertThat(productos.get(0).getNombre(),equalToIgnoringCase("Mogul"));

      }

    @Test
    public void losHijosDelUsuarioDebenAparecerEnLaTablaDeProductos() {
        usuarioMock.setId(1L);
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);

        when(carritoMock.getItems()).thenReturn(new ArrayList<>());
        when(serviCarritomock.obtenerOCrearCarrito(any())).thenReturn(carritoMock);
        when(hijoMock.getNombre()).thenReturn("Santi");
        when(serviHijoMock.obtenerHijosPorUsuario(any())).thenReturn(List.of(hijoMock));

        ModelAndView mv = distriControlador.verDistribucion(sessionMock);

        List<Hijo> hijos = (List<Hijo>) mv.getModel().get("hijos");
        assertThat(hijos.size(),org.hamcrest.Matchers.equalTo(1));
        assertThat(hijos.get(0).getNombre(),equalToIgnoringCase("Santi"));
    }

}
