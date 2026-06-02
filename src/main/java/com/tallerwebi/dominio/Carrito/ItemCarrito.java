package com.tallerwebi.dominio.Carrito;

import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Productos.Producto;
import javax.persistence.*;

@Entity
public class ItemCarrito {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Producto producto;


    @ManyToOne
    private Hijo hijo;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hijo getHijo() {
        return hijo;
    }

    public void setHijo(Hijo hijo) {
        this.hijo = hijo;
    }
}
