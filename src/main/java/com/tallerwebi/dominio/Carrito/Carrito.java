package com.tallerwebi.dominio.Carrito;

import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Carrito {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<ItemCarrito> items;

  @ManyToOne
  private Usuario usuario;

  private static final int CANTIDAD_MINIMA_PRODUCTO = 1;

  public Carrito() {
    this.items = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public List<ItemCarrito> getItems() {
    return items;
  }

  public void setItems(List<ItemCarrito> items) {
    this.items = items;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public void agregarProducto(Producto producto) {
    for (ItemCarrito item : items) {
      if (item.getProducto().getId().equals(producto.getId())) {
        item.setCantidad(item.getCantidad() + 1);
        return;
      }
    }

    items.add(new ItemCarrito(producto, 1));
  }

  public void eliminarProducto(long productoId) {
    items.removeIf(item -> item.getProducto().getId().equals(productoId));
  }

  public Double calcularTotal() {
    Double total = 0.0;

    for (ItemCarrito item : items) {
      total += item.getProducto().getPrecio() * item.getCantidad();
    }

    return total;
  }

  public void aumentarCantidad(long productoId) {
    for (ItemCarrito item : items) {
      if (item.getProducto().getId().equals(productoId)) {
        item.setCantidad(item.getCantidad() + 1);
        return;
      }
    }
  }

  public void disminuirCantidad(long productoId) {
    for (ItemCarrito item : items) {
      if (item.getProducto().getId().equals(productoId)) {
        if (item.getCantidad() > CANTIDAD_MINIMA_PRODUCTO) {
          item.setCantidad(item.getCantidad() - 1);
          return;
        }
      }
    }
  }
}
