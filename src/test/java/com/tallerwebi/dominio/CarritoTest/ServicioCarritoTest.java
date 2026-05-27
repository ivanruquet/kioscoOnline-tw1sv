package com.tallerwebi.dominio.CarritoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Carrito.Carrito;
import com.tallerwebi.dominio.Carrito.ItemCarrito;
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

    Carrito carrito = new Carrito();

    when(repositorioProductoMock.buscarProductoPorId(producto.getId())).thenReturn(producto);

    //ejecucion
    servicioCarrito.agregarProducto(producto.getId(), carrito);

    int resultado = carrito.getItems().size();

    //validacion
    assertEquals(1, resultado);
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

    Carrito carrito = new Carrito();

    when(repositorioProductoMock.buscarProductoPorId(producto1.getId())).thenReturn(producto1);
    when(repositorioProductoMock.buscarProductoPorId(producto2.getId())).thenReturn(producto2);

    servicioCarrito.agregarProducto(producto1.getId(), carrito);
    servicioCarrito.agregarProducto(producto2.getId(), carrito);

    //ejecucion
    Double total = servicioCarrito.calcularTotal(carrito);

    //validacion
    assertEquals(300.0, total, 0.01);
  }

  @Test
  public void dadoUnProductoYaExistenteEnCarritoCuandoLoAgregoNoSeDuplica() {
    //preparacion
    Producto producto = new Producto();
    producto.setId(1L);
    producto.setNombre("Alfajor");

    Carrito carrito = new Carrito();

    when(repositorioProductoMock.buscarProductoPorId(producto.getId())).thenReturn(producto);

    servicioCarrito.agregarProducto(producto.getId(), carrito);

    //ejecucion
    servicioCarrito.agregarProducto(producto.getId(), carrito);

    int resultado = carrito.getItems().size();

    //validacion
    assertEquals(1, resultado);
  }

  @Test
  public void dadoUnProductoEnCarritoCuandoLoEliminoEntoncesSeRemueveCorrectamente() {
    //preparacion
    Producto producto = new Producto();
    producto.setId(1L);
    producto.setNombre("Alfajor");

    Carrito carrito = new Carrito();

    when(repositorioProductoMock.buscarProductoPorId(producto.getId())).thenReturn(producto);

    servicioCarrito.agregarProducto(producto.getId(), carrito);

    //ejecucion
    servicioCarrito.eliminarProducto(producto.getId(), carrito);

    int resultado = carrito.getItems().size();

    //validacion
    assertEquals(0, resultado);
  }

  @Test
  public void dadoUnProductoEnCarritoCuandoAumentoCantidadEntoncesIncrementaEnUno() {
    //preparacion
    Producto producto = new Producto();
    producto.setId(1L);
    producto.setNombre("Alfajor");

    Carrito carrito = new Carrito();

    when(repositorioProductoMock.buscarProductoPorId(producto.getId())).thenReturn(producto);

    servicioCarrito.agregarProducto(producto.getId(), carrito);

    //ejecucion
    servicioCarrito.aumentarCantidad(producto.getId(), carrito);

    int resultado = carrito.getItems().get(0).getCantidad();

    //validacion
    assertEquals(2, resultado);
  }

  @Test
  public void dadoUnProductoEnCarritoCuandoRestoCantidadEntoncesRestoEnUno() {
    //preparacion
    Producto producto = new Producto();
    producto.setId(1L);
    producto.setNombre("Alfajor");

    Carrito carrito = new Carrito();

    when(repositorioProductoMock.buscarProductoPorId(producto.getId())).thenReturn(producto);

    servicioCarrito.agregarProducto(producto.getId(), carrito);
    servicioCarrito.aumentarCantidad(producto.getId(), carrito);

    //ejecucion
    servicioCarrito.disminuirCantidad(producto.getId(), carrito);

    int resultado = carrito.getItems().get(0).getCantidad();

    //validacion
    assertEquals(1, resultado);
  }
}
