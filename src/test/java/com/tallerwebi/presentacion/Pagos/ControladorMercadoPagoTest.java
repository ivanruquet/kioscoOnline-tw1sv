package com.tallerwebi.presentacion.Pagos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Carrito.Carrito;
import com.tallerwebi.dominio.Carrito.ItemCarrito;
import com.tallerwebi.dominio.Carrito.ServicioCarrito;
import com.tallerwebi.dominio.Pagos.ServicioMercadoPago;
import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorMercadoPagoTest {

  private ServicioMercadoPago servicioMercadoPago;
  private ServicioCarrito servicioCarrito; // <-- NUEVO MOCK
  private ControladorMercadoPago controladorMercadoPago;
  private HttpSession sessionMock;
  private Usuario usuarioMock;

  @BeforeEach
  public void init() {
    this.servicioMercadoPago = mock(ServicioMercadoPago.class);
    this.servicioCarrito = mock(ServicioCarrito.class); // <-- Inicializamos el mock del carrito

    // Le pasamos ambos servicios al controlador unificado
    this.controladorMercadoPago =
      new ControladorMercadoPago(this.servicioMercadoPago, this.servicioCarrito);
    this.sessionMock = mock(HttpSession.class);

    // Preparamos un usuario mockeado con ID ficticio
    this.usuarioMock = mock(Usuario.class);
    when(this.usuarioMock.getId()).thenReturn(1L);
    // Hacemos que la sesión siempre devuelva este usuario para simular que está logueado
    when(this.sessionMock.getAttribute("USUARIO")).thenReturn(this.usuarioMock);
  }

  @Test
  public void siElCarritoEstaVacioAlPagarDebeRedirigirAlCarritoConMensajeDeError() {
    // 1. Given
    Carrito carritoVacio = new Carrito();
    carritoVacio.setItems(new ArrayList<>());

    when(this.servicioCarrito.obtenerOCrearCarrito(1L)).thenReturn(carritoVacio);

    // 2. When:
    ModelAndView modelAndView = this.controladorMercadoPago.pagar(this.sessionMock);

    // 3. Then
    assertThat(modelAndView.getViewName(), equalTo("redirect:/carrito"));
    assertThat(
      modelAndView.getModel().get("error").toString(),
      equalTo("El carrito no puede estar vacío")
    );
  }

  @Test
  public void alPagarDebeBuscarElCarritoEnLaBaseDeDatosYRedirigirAlCheckoutDeMercadoPago() {
    // 1. Given
    Carrito carritoConProducto = new Carrito();
    List<ItemCarrito> items = new ArrayList<>();
    Producto producto = new Producto();
    items.add(new ItemCarrito(producto, 1));
    carritoConProducto.setItems(items);

    when(this.servicioCarrito.obtenerOCrearCarrito(1L)).thenReturn(carritoConProducto);

    when(this.servicioMercadoPago.crearPreferenciaDePago(carritoConProducto))
      .thenReturn("https://www.mercadopago.com.ar/checkout/v1/redirect-real");

    // 2. When
    ModelAndView modelAndView = this.controladorMercadoPago.pagar(this.sessionMock);

    // 3. Then
    assertThat(
      modelAndView.getViewName(),
      equalTo("redirect:https://www.mercadopago.com.ar/checkout/v1/redirect-real")
    );
  }

  @Test
  public void siElUsuarioNoEstaLogueadoDebeRedirigirAlLogin() {
    // La sesión no tiene al usuario cargado
    when(this.sessionMock.getAttribute("USUARIO")).thenReturn(null);

    // Intenta pagar
    ModelAndView modelAndView = this.controladorMercadoPago.pagar(this.sessionMock);

    // Redirige al login (Cubre el primer IF del controlador)
    assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
  }

  @Test
  public void siMercadoPagoFallaDebeRedirigirAlCarritoConMensajeDeError() {
    // 1. Given
    Carrito carrito = new Carrito();
    List<ItemCarrito> items = new ArrayList<>();
    Producto producto = new Producto();
    items.add(new ItemCarrito(producto, 1));
    carrito.setItems(items);

    when(servicioCarrito.obtenerOCrearCarrito(1L)).thenReturn(carrito);

    // 2. Given
    when(servicioMercadoPago.crearPreferenciaDePago(carrito)).thenReturn(null);

    // 3. When
    ModelAndView mav = controladorMercadoPago.pagar(sessionMock);

    // 4. Then
    assertThat(mav.getViewName(), equalTo("redirect:/carrito"));
    assertThat(
      mav.getModel().get("error").toString(),
      equalTo("No se pudo conectar con Mercado Pago. Intente más tarde.")
    );
  }

  @Test
  public void siElUsuarioNoEstaLogueadoDebeRedirigirAlLoginCuandoEntraAlPagoExitoso() {
    // La sesión no tiene al usuario cargado
    when(this.sessionMock.getAttribute("USUARIO")).thenReturn(null);

    // Intenta ingresar a pagar-exitoso sin estar logeado
    ModelAndView modelAndView = this.controladorMercadoPago.mostrarPagoExitoso(this.sessionMock);
    // Redirige al login ()
    assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
  }

  @Test
  public void ElUsuarioEstaLogueadoCuandoEntraAlPagoExitosoSinItemsEnElCarrito() {
    // La sesión ya tiene al usuario cargado
    Carrito carritoVacio = new Carrito();
    carritoVacio.setItems(new ArrayList<>());
    //tiene carrito vacio
    when(this.servicioCarrito.obtenerOCrearCarrito(1L)).thenReturn(carritoVacio);
    // Intenta ingresar a pagar-exitoso estando logeado pero sin carrito con items
    ModelAndView mav = this.controladorMercadoPago.mostrarPagoExitoso(this.sessionMock);
    // Redirige al carrito con error ()
    assertThat(mav.getViewName(), equalTo("redirect:/carrito"));
    assertThat(
      mav.getModel().get("error").toString(),
      equalTo("Debés agregar items y realizar una compra primero.")
    );
  }

  @Test
  public void ElUsuarioEstaLogueadoCuandoEntraAlPagoExitosoConItemsEnElCarritoDebeDevolverEstosEnElModel() {
    Carrito carritoConProducto = new Carrito();
    List<ItemCarrito> items = new ArrayList<>();
    Producto producto = new Producto();
    items.add(new ItemCarrito(producto, 1));
    carritoConProducto.setItems(items);

    when(this.servicioCarrito.obtenerOCrearCarrito(1L)).thenReturn(carritoConProducto);

    // 2. When
    ModelAndView modelAndView = this.controladorMercadoPago.mostrarPagoExitoso(this.sessionMock);

    // 3. Then
    assertThat(modelAndView.getViewName(), equalTo("pago-exitoso"));
    assertThat(modelAndView.getModel().get("itemsComprados"), equalTo(items));
  }
}
