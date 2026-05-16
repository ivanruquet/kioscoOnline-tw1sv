package com.tallerwebi.dominio.Productos;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioProducto")
@Transactional
public class ServicioProductoImpl implements ServicioProducto {

  private RepositorioProducto repositorioProducto;

  @Autowired
  public ServicioProductoImpl(RepositorioProducto repositorioProducto) {
    this.repositorioProducto = repositorioProducto;
  }

  @Override
  public List<Producto> obtenerListadoProductos() {
    return this.repositorioProducto.listarProductos();
  }
}
