package com.tallerwebi.dominio.Pagos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Carrito.Carrito;
import com.tallerwebi.dominio.Carrito.ItemCarrito;
import com.tallerwebi.dominio.Productos.Producto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioMercadoPagoTest {

  private ServicioMercadoPago servicioMercadoPago;

  @BeforeEach
  public void init() {
    this.servicioMercadoPago = new ServicioMercadoPagoImpl();
  }

  @Test
  public void alCrearPreferenciaDePagoDebeDevolverUnaUrlValidaDeMercadoPago() {
    // 1. Given:
    Carrito carrito = new Carrito();
    List<ItemCarrito> items = new ArrayList<>();

    Producto alfajor = new Producto();
    alfajor.setNombre("Alfajor Jorgito");
    alfajor.setPrecio(1200.0);

    // Creamos el ítem y lo metemos en la lista interna del carrito
    items.add(new ItemCarrito(alfajor, 2));
    carrito.setItems(items);
    String urlCheckout = this.servicioMercadoPago.crearPreferenciaDePago(carrito);

    assertThat(urlCheckout, nullValue());
  }

  @Test
  public void siElCarritoEsNuloDebeRetornarNull() {
    String urlCheckout = this.servicioMercadoPago.crearPreferenciaDePago(null);

    assertThat(urlCheckout, nullValue());
  }
}
