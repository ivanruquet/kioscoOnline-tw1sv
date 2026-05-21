package com.tallerwebi.dominio.Hijos;

import java.util.List;

public interface RepositorioHijo {
  List<Hijo> listarHijos(Long idPadre);
}
