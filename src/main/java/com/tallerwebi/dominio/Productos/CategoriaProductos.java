package com.tallerwebi.dominio.Productos;

import javax.persistence.*;

@Entity
public class CategoriaProductos {

  @Id
  private Long id;

  @Column(nullable = false)
  private String nombreCategoria;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombreCategoria() {
    return nombreCategoria;
  }

  public void setNombreCategoria(String nombreCategoria) {
    this.nombreCategoria = nombreCategoria;
  }
}
