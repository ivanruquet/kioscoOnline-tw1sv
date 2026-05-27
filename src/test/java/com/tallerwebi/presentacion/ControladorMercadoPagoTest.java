package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Carrito.ItemCarrito;
import com.tallerwebi.dominio.Pagos.ServicioMercadoPago;
import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.presentacion.Pagos.ControladorMercadoPago;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorMercadoPagoTest {

  private ServicioMercadoPago servicioMercadoPago;
  private ControladorMercadoPago controladorMercadoPago;
  private HttpSession sessionMock;

  @Before
  public void init() {
    this.servicioMercadoPago = mock(ServicioMercadoPago.class);
    this.controladorMercadoPago = new ControladorMercadoPago(this.servicioMercadoPago);
    this.sessionMock = mock(HttpSession.class);
  }

  @Test
  public void siElCarritoEstaVacioAlPagarDebeRedirigirAlCarritoConMensajeDeError() {
    List<ItemCarrito> carritoVacio = new ArrayList<>();
    when(this.sessionMock.getAttribute("carrito")).thenReturn(carritoVacio);
    ModelAndView modelAndView = this.controladorMercadoPago.pagar(this.sessionMock);

    assertThat(modelAndView.getViewName(), equalTo("redirect:/carrito"));
    assertThat(
      modelAndView.getModel().get("error").toString(),
      equalTo("El carrito no puede estar vacío")
    );
  }

  @Test
  public void alPagarDebeBuscarElCarritoEnLaSesionYRedirigirAlCheckoutDeMercadoPago() {
    List<ItemCarrito> carritoConProducto = new ArrayList<>();
    Producto producto = new Producto();
    carritoConProducto.add(new ItemCarrito(producto, 1));
    when(this.sessionMock.getAttribute("carrito")).thenReturn(carritoConProducto);

    when(this.servicioMercadoPago.crearPreferenciaDePago(carritoConProducto))
      .thenReturn("https://www.mercadopago.com.ar/checkout/v1/redirect-real");

    ModelAndView modelAndView = this.controladorMercadoPago.pagar(this.sessionMock);

    assertThat(
      modelAndView.getViewName(),
      equalTo("redirect:https://www.mercadopago.com.ar/checkout/v1/redirect-real")
    );
  }
}
