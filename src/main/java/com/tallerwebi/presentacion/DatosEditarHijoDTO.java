package com.tallerwebi.presentacion;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class DatosEditarHijoDTO {

  @NotNull(message = "ID de Hijo obligatorio")
  private Long idHijo;

  private String nombreH;
  private String apellidoH;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date fechaH;

  private String anio;
  private String division;

  private MultipartFile fotoPerfilH;

  public String getAnio() {
    return anio;
  }

  public void setAnio(String anio) {
    this.anio = anio;
  }

  public String getNombreH() {
    return nombreH;
  }

  public void setNombreH(String nombreH) {
    this.nombreH = nombreH;
  }

  public String getApellidoH() {
    return apellidoH;
  }

  public void setApellidoH(String apellidoH) {
    this.apellidoH = apellidoH;
  }

  public String getDivision() {
    return division;
  }

  public void setDivision(String division) {
    this.division = division;
  }

  public Date getFechaH() {
    return fechaH;
  }

  public void setFechaH(Date fechaH) {
    this.fechaH = fechaH;
  }

  public MultipartFile getFotoPerfilH() {
    return fotoPerfilH;
  }

  public void setFotoPerfilH(MultipartFile fotoPerfilH) {
    this.fotoPerfilH = fotoPerfilH;
  }

  public Long getIdHijo() {
    return idHijo;
  }

  public void setIdHijo(Long idHijo) {
    this.idHijo = idHijo;
  }
}
