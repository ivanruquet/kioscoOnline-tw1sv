package com.tallerwebi.dominio.Productos;

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
    return this.repositorioProducto.listarProductos();
  }

  @Override
  public List<Producto> obtenerListadoProductosFiltrado(String categoria) {
    return this.repositorioProducto.listarProductosFiltrados(categoria);
  }

  @Override
  public List<CategoriaProductos> obtenerListadoCategorias() {
    return this.repositorioProducto.listarCategorias();
  }

  @Override
  public List<Producto> buscarProductosPorNombre(String texto) {
    return this.repositorioProducto.buscarProductos(texto);
  }
}
