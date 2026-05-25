package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

  private final SessionFactory sessionFactory;

  @Autowired
  public RepositorioUsuarioImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Usuario buscarUsuario(String email, String password) {
    //    /* Se utiliza sessionFactory.getCurrentSession() directamente para que el recurso sea gestionado por Spring y PMD no exija cerrarlo manualmente */
    //    return (Usuario) sessionFactory
    //      .getCurrentSession()
    //      .createCriteria(Usuario.class)
    //      .add(Restrictions.eq("email", email))
    //      .add(Restrictions.eq("password", password))
    //      .uniqueResult(); DE LOS PROFES
    String hql = "FROM Usuario WHERE email=:email AND password=:password";
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    query.setParameter("email", email);
    query.setParameter("password", password);
    List<Usuario> usuariosResultado = query.getResultList();
    return usuariosResultado.isEmpty() ? null : usuariosResultado.get(0);
  }

  @Override
  public void guardar(Usuario usuario) {
    sessionFactory.getCurrentSession().save(usuario);
  }

  @Override
  public Usuario buscar(String email) {
    //    return (Usuario) sessionFactory
    //      .getCurrentSession()
    //      .createCriteria(Usuario.class)
    //      .add(Restrictions.eq("email", email))
    //      .uniqueResult(); DE LOS PROFES
    String hql = "FROM Usuario WHERE email=:email";
    Query query = sessionFactory.getCurrentSession().createQuery(hql);
    query.setParameter("email", email);
    List<Usuario> usuariosResultado = query.getResultList();
    return usuariosResultado.isEmpty() ? null : usuariosResultado.get(0);
  }

  @Override
  public void modificar(Usuario usuario) {
    sessionFactory.getCurrentSession().update(usuario);
  }
}
