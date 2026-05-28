package com.tallerwebi.dominio.Pagos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

import com.tallerwebi.dominio.Carrito.Carrito; // <-- NUEVO IMPORT
import com.tallerwebi.dominio.Carrito.ItemCarrito;
import com.tallerwebi.dominio.Productos.Producto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ServicioMercadoPagoTest {

  private ServicioMercadoPago servicioMercadoPago;

  @Before
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
    carrito.setItems(items); // <-- Vinculamos la lista al objeto Carrito

    // 2. When:
    String urlCheckout = this.servicioMercadoPago.crearPreferenciaDePago(carrito);

    // 3. Then
    assertThat(urlCheckout, notNullValue());
    assertThat(urlCheckout, containsString("mercadopago.com"));
  }
}
