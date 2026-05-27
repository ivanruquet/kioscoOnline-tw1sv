package com.tallerwebi.dominio.Pagos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

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
    // 1. Given: Preparamos un carrito con un producto real de tu kiosco
    List<ItemCarrito> carrito = new ArrayList<>();

    Producto alfajor = new Producto();
    alfajor.setNombre("Alfajor Jorgito");
    alfajor.setPrecio(1200.0);

    carrito.add(new ItemCarrito(alfajor, 2));

    String urlCheckout = this.servicioMercadoPago.crearPreferenciaDePago(carrito);

    assertThat(urlCheckout, notNullValue());
    assertThat(urlCheckout, containsString("mercadopago.com"));
    assertThat(urlCheckout, containsString("pref_id"));
  }
}
