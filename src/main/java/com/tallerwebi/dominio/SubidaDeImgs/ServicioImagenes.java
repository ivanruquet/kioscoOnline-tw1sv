package com.tallerwebi.dominio.SubidaDeImgs;

import org.springframework.web.multipart.MultipartFile;

public interface ServicioImagenes {
  String subirImagen(MultipartFile archivo, String carpeta);
}
