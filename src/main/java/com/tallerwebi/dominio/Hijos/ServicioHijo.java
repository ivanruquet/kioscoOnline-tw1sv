package com.tallerwebi.dominio.Hijos;

import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.List;

public interface ServicioHijo {
  List<Hijo> obtenerHijosPorUsuario(Long idUsuario);

  void guardarHijo(Hijo hijo, Usuario usuario);
}
