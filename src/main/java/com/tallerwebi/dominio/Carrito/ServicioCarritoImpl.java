package com.tallerwebi.dominio.Carrito;

import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Productos.RepositorioProducto;
import com.tallerwebi.dominio.excepcion.ProductoNoEncontradoException;
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
  public void agregarProducto(long productoId, Carrito carrito) {
    Producto producto = repositorioProducto.buscarProductoPorId(productoId);

    if (producto == null) {
      throw new ProductoNoEncontradoException("El producto no fue encontrado");
    }

    carrito.agregarProducto(producto);
  }

  @Override
  public Double calcularTotal(Carrito carrito) {
    return carrito.calcularTotal();
  }

  @Override
  public void eliminarProducto(long productoId, Carrito carrito) {
    carrito.eliminarProducto(productoId);
  }

  @Override
  public void aumentarCantidad(long productoId, Carrito carrito) {
    carrito.aumentarCantidad(productoId);
  }

  @Override
  public void disminuirCantidad(long productoId, Carrito carrito) {
    carrito.disminuirCantidad(productoId);
  }
}
