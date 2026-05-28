package com.tallerwebi.dominio.ProductoTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Productos.*;
import com.tallerwebi.dominio.excepcion.ProductoNoEncontradoException;
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
  public void cuandoSeSolicitanTodosLosProductosDebeBuscarEnElRepoYDevolverlo() {
    //preparacion
    List<Producto> listaSimulada = List.of(productoMock);
    when(repositorioProductoMock.listarProductos()).thenReturn(listaSimulada);

    //ejecucion
    List<Producto> productosObtenidos = servicioProducto.obtenerListadoProductos();

    //validacion
    assertThat(productosObtenidos, hasSize(1));
    verify(repositorioProductoMock, times(1)).listarProductos();
  }

  @Test
  public void cuandoSeSolicitanTodosLosProductosYNoHay_debeLanzarExcepcion() {
    List<Producto> listaSimulada = List.of();
    when(repositorioProductoMock.listarProductos()).thenReturn(listaSimulada);

    assertThrows(
      ProductoNoEncontradoException.class,
      () -> servicioProducto.obtenerListadoProductos()
    );
  }

  @Test
  public void cuandoSeFiltraPorCategoriaDebeRetornarLosProductosDeEsaCategoria() {
    String categoria = "categoria";
    List<Producto> listaSimulada = List.of(productoMock);
    when(repositorioProductoMock.listarProductosFiltrados(categoria)).thenReturn(listaSimulada);

    List<Producto> productosObtenidos = servicioProducto.obtenerListadoProductosFiltrado(categoria);
    assertThat(productosObtenidos, hasSize(1));
  }

  @Test
  public void cuandoSeFiltraPorCategoriaYNoHayProductosDebeLanzarExcepcion() {
    when(repositorioProductoMock.listarProductosFiltrados("categoria")).thenReturn(List.of()); //devuelve lista vacia

    assertThrows(
      ProductoNoEncontradoException.class,
      () -> servicioProducto.obtenerListadoProductosFiltrado("categoria")
    );
  }

  @Test
  public void cuandoSeBuscaPorNombreDebeRetornarLosProductosConEseNombre() {
    String busqueda = "busqueda";
    Producto productoMock2 = Mockito.mock(Producto.class);
    List<Producto> listaSimulada = List.of(productoMock, productoMock2);
    when(repositorioProductoMock.buscarProductos(busqueda)).thenReturn(listaSimulada);

    List<Producto> productosObtenidos = servicioProducto.buscarProductosPorNombre(busqueda);
    assertThat(productosObtenidos, hasSize(2));
  }

  @Test
  public void siSeBuscaUnProductoYNoExisteDebeLanzarExcepcion() {
    when(repositorioProductoMock.buscarProductos("nombre")).thenReturn(List.of()); //que devuelva una lista vacia

    assertThrows(
      ProductoNoEncontradoException.class,
      () -> servicioProducto.buscarProductosPorNombre("nombre")
    );
  }
}
