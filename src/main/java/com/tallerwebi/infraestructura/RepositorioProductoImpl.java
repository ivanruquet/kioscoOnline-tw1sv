package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Productos.RepositorioProducto;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioProducto")
public class RepositorioProductoImpl implements RepositorioProducto {

  private SessionFactory sessionFactory;

  @Autowired
  public RepositorioProductoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public List<Producto> listarProductos() {
    return this.sessionFactory.getCurrentSession()
      .createQuery("FROM Producto", Producto.class)
      .getResultList();
  }

  @Override
  public Producto buscarProductoPorId(long id) {
    return this.sessionFactory.getCurrentSession().get(Producto.class, id);
  }
}
