package com.tallerwebi.dominio.Productos;

import java.util.List;

public interface ServicioProducto {
  List<Producto> obtenerListadoProductos();

  List<Producto> obtenerListadoProductosFiltrado(String categoria);

  List<CategoriaProductos> obtenerListadoCategorias();

  List<Producto> buscarProductosPorNombre(String texto);
}
