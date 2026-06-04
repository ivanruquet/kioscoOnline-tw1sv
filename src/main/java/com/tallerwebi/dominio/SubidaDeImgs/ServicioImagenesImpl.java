package com.tallerwebi.dominio.SubidaDeImgs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServicioImagenesImpl implements ServicioImagenes {

  private final Cloudinary cloudinary;

  @Autowired
  public ServicioImagenesImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  public String subirImagen(MultipartFile archivo, String carpeta) {
    try {
      Map resultado = cloudinary
        .uploader()
        .upload(archivo.getBytes(), ObjectUtils.asMap("folder", carpeta));

      return resultado.get("secure_url").toString();
    } catch (IOException e) {
      throw new RuntimeException("Error al subir imagen", e);
    }
  }
}
