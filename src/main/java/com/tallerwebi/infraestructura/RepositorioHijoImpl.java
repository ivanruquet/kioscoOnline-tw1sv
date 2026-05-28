package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.RepositorioHijo;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioHijo")
public class RepositorioHijoImpl implements RepositorioHijo {

  private final SessionFactory sessionFactory;

  @Autowired
  public RepositorioHijoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public List<Hijo> listarHijos(Long idPadre) {
    String query = "FROM Hijo h WHERE h.padre.id=:idPadre";
    return this.sessionFactory.getCurrentSession()
      .createQuery(query, Hijo.class)
      .setParameter("idPadre", idPadre)
      .getResultList();
  }

  @Override
  public Hijo buscarPorId(Long id) {
    return sessionFactory.getCurrentSession().get(Hijo.class, id);
  }
}
