package com.tallerwebi.dominio.CarritoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Carrito.ServicioCarritoImpl;
import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Productos.RepositorioProducto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ServicioCarritoTest {

  @Mock
  private RepositorioProducto repositorioProductoMock;

  @InjectMocks
  private ServicioCarritoImpl servicioCarrito;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void dadoUnProductoExistenteCuandoLoAgregoAlCarritoEntoncesSeAgregaCorrectamente() {
    //preparacion
    Producto producto = new Producto();
    producto.setId(1L);
    producto.setNombre("Galletitas");

    List<Producto> carrito = new ArrayList<>();

    when(repositorioProductoMock.buscarProductoPorId(1L)).thenReturn(producto);

    //ejecucion
    List<Producto> carritoActualizado = servicioCarrito.agregarProducto(1L, carrito);

    //validacion
    assertEquals(1, carritoActualizado.size());
    assertEquals(producto, carritoActualizado.get(0));
  }

  @Test
  public void dadoUnCarritoConDosProductosCalculaCorrectamenteElTotal() {
    //preparacion
    Producto producto1 = new Producto();
    producto1.setId(1L);
    producto1.setNombre("Galletitas");
    producto1.setPrecio(100.0);

    Producto producto2 = new Producto();
    producto2.setId(2L);
    producto2.setNombre("Gaseosa");
    producto2.setPrecio(200.0);

    List<Producto> carrito = new ArrayList<>();
    carrito.add(producto1);
    carrito.add(producto2);

    //ejecucion
    Double total = servicioCarrito.calcularTotal(carrito);

    //validacion
    assertEquals(300.0, total, 0.01);
  }
}
