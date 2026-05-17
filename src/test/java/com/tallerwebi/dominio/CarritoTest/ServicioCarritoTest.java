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
}
