package com.tallerwebi.dominio.Carrito;

import com.tallerwebi.dominio.Productos.Producto;
import javax.persistence.*;

@Entity
public class ItemCarrito {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Producto producto;

  private Integer cantidad;

  public ItemCarrito(Producto producto, Integer cantidad) {
    this.producto = producto;
    this.cantidad = cantidad;
  }

  public ItemCarrito() {}

  public void setProducto(Producto producto) {
    this.producto = producto;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public Producto getProducto() {
    return producto;
  }

  public Integer getCantidad() {
    return cantidad;
  }
}
