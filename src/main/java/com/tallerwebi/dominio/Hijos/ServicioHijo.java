package com.tallerwebi.dominio.Hijos;

import java.util.List;

public interface ServicioHijo {
  List<Hijo> obtenerHijosPorUsuario(Long idUsuario);
}
