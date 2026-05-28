package com.tallerwebi.dominio.Hijos;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioHijo")
@Transactional
public class ServicioHijoImpl implements ServicioHijo {

  private final RepositorioHijo repoHijo;

  @Autowired
  public ServicioHijoImpl(RepositorioHijo repositorioHijo) {
    this.repoHijo = repositorioHijo;
  }

  @Override
  public List<Hijo> obtenerHijosPorUsuario(Long idUsuario) {
    return this.repoHijo.listarHijos(idUsuario);
  }
}
