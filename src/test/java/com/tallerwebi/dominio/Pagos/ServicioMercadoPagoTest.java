package com.tallerwebi.dominio.Pagos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Carrito.Carrito;
import com.tallerwebi.dominio.Carrito.ItemCarrito;
import com.tallerwebi.dominio.Pedidos.Pedido;
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

  //  @Test
  //  public void alCrearPreferenciaDePagoDebeDevolverUnaUrlValidaDeMercadoPago() {
  //    // 1. Given:
  //    Carrito carrito = new Carrito();
  //    List<ItemCarrito> items = new ArrayList<>();
  //
  //    Producto alfajor = new Producto();
  //    alfajor.setNombre("Alfajor Jorgito");
  //    alfajor.setPrecio(1200.0);
  //
  //    // Creamos el ítem y lo metemos en la lista interna del carrito
  //    items.add(new ItemCarrito(alfajor, 2));
  //    carrito.setItems(items);
  //    String urlCheckout = this.servicioMercadoPago.crearPreferenciaDePago(carrito);
  //
  //    assertThat(urlCheckout, nullValue());
  //  }
  // ...

  @Test
  public void siElPedidoTieneItemsDebeIntentarCrearPreferenciaPeroRetornaNullPorTokenInvalido() {
    // Given
    Producto producto = new Producto();
    producto.setNombre("Alfajor Jorgito");

    var item = new com.tallerwebi.dominio.Pedidos.ItemPedido();
    item.setProducto(producto);
    item.setCantidad(2);
    item.setPrecioUnitario(1200.0);

    Pedido pedido = new Pedido();
    pedido.setItems(List.of(item));

    List<Pedido> pedidos = List.of(pedido);

    // When
    String urlCheckout = servicioMercadoPago.crearPreferenciaDePago(pedidos);

    // Then
    assertThat(urlCheckout, nullValue());
  }

  @Test
  public void siLaListaDePedidosEsNulaDebeRetornarNull() {
    String urlCheckout = servicioMercadoPago.crearPreferenciaDePago(null);

    assertThat(urlCheckout, nullValue());
  }

  @Test
  public void siLaListaDePedidosEstaVaciaDebeRetornarNull() {
    List<Pedido> pedidos = new ArrayList<>();

    String urlCheckout = servicioMercadoPago.crearPreferenciaDePago(pedidos);

    assertThat(urlCheckout, nullValue());
  }

  @Test
  public void siElPedidoNoTieneItemsDebeRetornarNull() {
    Pedido pedido = new Pedido();
    pedido.setItems(new ArrayList<>());

    List<Pedido> pedidos = List.of(pedido);

    String urlCheckout = servicioMercadoPago.crearPreferenciaDePago(pedidos);

    assertThat(urlCheckout, nullValue());
  }
}
