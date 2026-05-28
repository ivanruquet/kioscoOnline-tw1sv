package com.tallerwebi.dominio.Productos;

import com.tallerwebi.dominio.excepcion.ProductoNoEncontradoException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioProducto")
@Transactional
public class ServicioProductoImpl implements ServicioProducto {

  private final RepositorioProducto repositorioProducto;

  @Autowired
  public ServicioProductoImpl(RepositorioProducto repositorioProducto) {
    this.repositorioProducto = repositorioProducto;
  }

  @Override
  public List<Producto> obtenerListadoProductos() {
    List<Producto> productosTodos = this.repositorioProducto.listarProductos();
    if (productosTodos.isEmpty()) {
      throw new ProductoNoEncontradoException("No se encontraron productos en la base de datos");
    }
    return productosTodos;
  }

  @Override
  public List<Producto> obtenerListadoProductosFiltrado(String categoria) {
    List<Producto> productosFiltrados =
      this.repositorioProducto.listarProductosFiltrados(categoria);
    if (productosFiltrados.isEmpty()) {
      throw new ProductoNoEncontradoException("No se encontraron productos en esta categoria");
    }
    return productosFiltrados;
  }

  @Override
  public List<CategoriaProductos> obtenerListadoCategorias() {
    return this.repositorioProducto.listarCategorias();
  }

  @Override
  public List<Producto> buscarProductosPorNombre(String texto) {
    List<Producto> productosBuscados = this.repositorioProducto.buscarProductos(texto);
    if (productosBuscados.isEmpty()) {
      throw new ProductoNoEncontradoException(
        "No se encontró ninguna coincidencia para: " + texto + ". Intente otra búsqueda"
      );
    }
    return productosBuscados;
  }
}
