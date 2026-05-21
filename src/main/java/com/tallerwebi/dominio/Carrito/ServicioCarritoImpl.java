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
  private static final int CANTIDAD_MINIMA_PRODUCTO = 1;

  public ServicioCarritoImpl(RepositorioProducto repositorioProducto) {
    this.repositorioProducto = repositorioProducto;
  }

  @Override
  public List<ItemCarrito> agregarProducto(long id, List<ItemCarrito> carrito) {
    Producto producto = repositorioProducto.buscarProductoPorId(id);

    if (producto == null) {
      throw new ProductoNoEncontradoException();
    }

    for (ItemCarrito item : carrito) {
      if (item.getProducto().getId().equals(id)) {
        item.setCantidad(item.getCantidad() + 1);
        return carrito;
      }
    }

    carrito.add(new ItemCarrito(producto, 1));
    return carrito;
  }

  @Override
  public Double calcularTotal(List<ItemCarrito> carrito) {
    Double total = 0.0;

    for (ItemCarrito item : carrito) {
      total += item.getProducto().getPrecio() * item.getCantidad();
    }

    return total;
  }

  @Override
  public List<ItemCarrito> eliminarProducto(long id, List<ItemCarrito> carrito) {
    carrito.removeIf(item -> item.getProducto().getId().equals(id));

    return carrito;
  }

  @Override
  public List<ItemCarrito> aumentarCantidad(long id, List<ItemCarrito> carrito) {
    for (ItemCarrito item : carrito) {
      if (item.getProducto().getId().equals(id)) {
        item.setCantidad(item.getCantidad() + 1);

        return carrito;
      }
    }

    return carrito;
  }

  @Override
  public List<ItemCarrito> restarCantidad(long id, List<ItemCarrito> carrito) {
    for (ItemCarrito item : carrito) {
      if (item.getProducto().getId().equals(id)) {
        if (item.getCantidad() > CANTIDAD_MINIMA_PRODUCTO) {
          item.setCantidad(item.getCantidad() - 1);
        }

        return carrito;
      }
    }

    return carrito;
  }
}
