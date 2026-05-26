package com.tallerwebi.dominio.Usuario;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DatosPersonales {

  @Column(nullable = false, unique = true)
  private Long dni;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  private String apellido;

  @Column(nullable = false)
  private Long celular;

  @Column
  private String fotoPerfil;

  // getters y setters
  public Long getCelular() {
    return celular;
  }

  public void setCelular(Long celular) {
    this.celular = celular;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getFotoPerfil() {
    return fotoPerfil;
  }

  public void setFotoPerfil(String foto) {
    this.fotoPerfil = foto;
  }

  public Long getDni() {
    return dni;
  }

  public void setDni(Long dni) {
    this.dni = dni;
  }
}
