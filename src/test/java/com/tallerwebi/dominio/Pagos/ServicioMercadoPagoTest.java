package com.tallerwebi.dominio.Pagos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.tallerwebi.dominio.Carrito.Carrito;
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

  @Test
  public void siElCarritoEsNuloDebeCapturarLaExcepcionYDevolverNull() {
    // Given: Pasamos un objeto nulo a propósito para romper la estructura del loop
    Carrito carritoNulo = null;

    // When: Se ejecuta la solicitud
    String urlCheckout = this.servicioMercadoPago.crearPreferenciaDePago(carritoNulo);

    // Then: Cae en el bloque catch y retorna null de forma segura sin colgar el backend
    assertThat(urlCheckout, nullValue());
  }
}
