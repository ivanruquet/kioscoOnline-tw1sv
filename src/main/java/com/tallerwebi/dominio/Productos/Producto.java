package com.tallerwebi.dominio.Productos;

import javax.persistence.*;

@Entity
public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nombre;

  @Column
  private String descripcion;

  @Column(nullable = false)
  private double precio;

  @Column
  private String imagen;

  @ManyToOne
  @JoinColumn(name = "categoria_id") //nombre de la columna en la BD
  private CategoriaProductos categoria; //objeto java

  public CategoriaProductos getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaProductos categoria) {
    this.categoria = categoria;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getImagen() {
    return imagen;
  }

  public void setImagen(String imagen) {
    this.imagen = imagen;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }
}
