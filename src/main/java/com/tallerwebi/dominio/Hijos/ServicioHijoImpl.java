package com.tallerwebi.dominio.Hijos;

import com.tallerwebi.dominio.Usuario.Usuario;
import com.tallerwebi.dominio.excepcion.HijoExistenteException;
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

  @Override
  public void guardarHijo(Hijo hijo, Usuario usuario) {
    if (repoHijo.existeHijoPorDni(hijo.getDni())) {
      throw new HijoExistenteException();
    }
    hijo.setPadre(usuario);
    repoHijo.guardar(hijo);
  }
}
