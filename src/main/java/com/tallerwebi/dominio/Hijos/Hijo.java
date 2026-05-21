package com.tallerwebi.dominio.Hijos;

import com.tallerwebi.dominio.Usuario;
import java.util.Date;
import javax.persistence.*;

@Entity
public class Hijo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  private String curso; //CREO QUE LO MEJOR SERIA TENER UNA TABLA CURSO CON LOS CURSOS YA PRECARGADOS

  @Column(nullable = false)
  private String fotoPerfil;

  @Column
  private Date fechaNac;

  //Hijos * -------1 padre
  @ManyToOne
  @JoinColumn(name = "idPadre")
  private Usuario padre;

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFotoPerfil() {
    return fotoPerfil;
  }

  public void setFotoPerfil(String fotoPerfil) {
    this.fotoPerfil = fotoPerfil;
  }

  public Date getFechaNac() {
    return fechaNac;
  }

  public void setFechaNac(Date fechaNac) {
    this.fechaNac = fechaNac;
  }

  public String getCurso() {
    return curso;
  }

  public void setCurso(String curso) {
    this.curso = curso;
  }

  public Usuario getPadre() {
    return padre;
  }

  public void setPadre(Usuario padre) {
    this.padre = padre;
  }
}
