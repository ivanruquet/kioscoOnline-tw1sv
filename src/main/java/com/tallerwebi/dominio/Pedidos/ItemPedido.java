package com.tallerwebi.dominio.Pedidos;

import com.tallerwebi.dominio.Productos.Producto;
import javax.persistence.*;

@Entity
public class ItemPedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "producto_id")
  private Producto producto;

  @Column(nullable = false)
  private Integer cantidad;

  @Column(nullable = false)
  private Double precioUnitario;

  @ManyToOne
  @JoinColumn(name = "pedido_id")
  private Pedido pedido;

  public ItemPedido() {}

  public ItemPedido(Producto producto, Integer cantidad) {
    this.producto = producto;
    this.cantidad = cantidad;
    this.precioUnitario = producto.getPrecio();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Producto getProducto() {
    return producto;
  }

  public void setProducto(Producto producto) {
    this.producto = producto;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public Double getPrecioUnitario() {
    return precioUnitario;
  }

  public void setPrecioUnitario(Double precioUnitario) {
    this.precioUnitario = precioUnitario;
  }

  public Double getSubtotal() {
    return precioUnitario * cantidad;
  }

  public Pedido getPedido() {
    return pedido;
  }

  public void setPedido(Pedido pedido) {
    this.pedido = pedido;
  }
}
