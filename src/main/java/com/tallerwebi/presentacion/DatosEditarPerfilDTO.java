package com.tallerwebi.presentacion;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

public class DatosEditarPerfilDTO {

  @Email(message = "El mail no es valido")
  private String email;

  @Pattern(regexp = "^[0-9]{8,15}$", message = "El celular debe contener solo números")
  private String celular;

  private MultipartFile fotoPerfil;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public MultipartFile getFotoPerfil() {
    return fotoPerfil;
  }

  public void setFotoPerfil(MultipartFile fotoPerfil) {
    this.fotoPerfil = fotoPerfil;
  }

  public String getCelular() {
    return celular;
  }

  public void setCelular(String celular) {
    this.celular = celular;
  }
}
