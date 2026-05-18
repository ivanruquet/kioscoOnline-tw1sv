package com.tallerwebi.dominio.Carrito;

import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Productos.RepositorioProducto;
import com.tallerwebi.dominio.excepcion.ProductoNoEncontradoException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service("servicioCarrito")
@Transactional
public class ServicioCarritoImpl implements ServicioCarrito {

  private RepositorioProducto repositorioProducto;

  public ServicioCarritoImpl(RepositorioProducto repositorioProducto) {
    this.repositorioProducto = repositorioProducto;
  }

  @Override
  public List<Producto> agregarProducto(long id, List<Producto> carrito) {
    Producto producto = repositorioProducto.buscarProductoPorId(id);

    if (producto == null) {
      throw new ProductoNoEncontradoException();
    }

    for (Producto productoEnCarrito : carrito) {
      if (productoEnCarrito.getId().equals(producto.getId())) {
        return carrito;
      }
    }

    carrito.add(producto);
    return carrito;
  }

  @Override
  public Double calcularTotal(List<Producto> carrito) {
    Double total = 0.0;

    for (Producto producto : carrito) {
      total += producto.getPrecio();
    }

    return total;
  }

  @Override
  public List<Producto> eliminarProducto(long id, List<Producto> carrito) {
    carrito.removeIf(producto -> producto.getId().equals(id));

    return carrito;
  }
}
