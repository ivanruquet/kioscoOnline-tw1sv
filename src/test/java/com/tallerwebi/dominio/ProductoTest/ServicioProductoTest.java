package com.tallerwebi.dominio.ProductoTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Productos.RepositorioProducto;
import com.tallerwebi.dominio.Productos.ServicioProducto;
import com.tallerwebi.dominio.Productos.ServicioProductoImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ServicioProductoTest {

  private ServicioProducto servicioProducto;
  private RepositorioProducto repositorioProductoMock;
  private Producto productoMock;

  @BeforeEach
  public void init() {
    repositorioProductoMock = Mockito.mock(RepositorioProducto.class);
    servicioProducto = new ServicioProductoImpl(repositorioProductoMock);
    productoMock = Mockito.mock(Producto.class);
  }

  @Test
  public void cuandoSeSolicitanProductosDebeBuscarEnElRepoYDevolverlo() {
    //preparacion
    List<Producto> listaSimulada = Arrays.asList(productoMock);
    when(repositorioProductoMock.listarProductos()).thenReturn(listaSimulada);

    //ejecucion
    List<Producto> productosObtenidos = servicioProducto.obtenerListadoProductos();

    //validacion
    assertThat(productosObtenidos, hasSize(1));
    verify(repositorioProductoMock, times(1)).listarProductos();
  }
}
