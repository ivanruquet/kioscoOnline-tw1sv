package com.tallerwebi.infraestructura;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.tallerwebi.dominio.Carrito.Carrito;
import com.tallerwebi.dominio.Carrito.RepositorioCarrito;
import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Usuario.Usuario;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateInfraestructuraTestConfig.class })
public class RepositorioCarritoTest {

  @Autowired
  private SessionFactory sessionFactory;

  private RepositorioCarrito repositorioCarrito;

  @BeforeEach
  public void init() {
    repositorioCarrito = new RepositorioCarritoImpl(sessionFactory);
  }

  @Test
  @Transactional
  @Rollback
  public void dadoUnUsuarioConCarritoCuandoBuscoPorUsuarioEntoncesObtengoElCarrito() {
    // preparacion
    Usuario usuario = new Usuario();
    usuario.setEmail("test@test.com");
    usuario.setPassword("123");
    usuario.setNombre("Test");
    usuario.setApellido("Test");
    usuario.setCelular(11543L);
    usuario.setDni(12345L);
    usuario.setRol("ADMIN");

    sessionFactory.getCurrentSession().save(usuario);

    Carrito carrito = new Carrito();
    carrito.setUsuario(usuario);

    sessionFactory.getCurrentSession().save(carrito);

    // ejecucion
    Carrito resultado = repositorioCarrito.buscarPorUsuario(usuario.getId());

    // validacion
    assertNotNull(resultado);

    assertEquals(usuario.getId(), resultado.getUsuario().getId());
  }

  @Test
  @Transactional
  @Rollback
  public void dadoUnCarritoConItemsCuandoSeGuardaEntoncesLosItemsPersisten() {
    // preparacion

    Usuario usuario = crearUsuarioValido();
    sessionFactory.getCurrentSession().save(usuario);

    Producto producto = new Producto();
    producto.setNombre("Galletitas");
    producto.setPrecio(100);
    producto.setCantidad(10);

    sessionFactory.getCurrentSession().save(producto);

    Carrito carrito = new Carrito();
    carrito.setUsuario(usuario);

    carrito.agregarProducto(producto);

    // ejecucion

    repositorioCarrito.guardar(carrito);

    sessionFactory.getCurrentSession().flush();
    sessionFactory.getCurrentSession().clear();

    Carrito resultado = repositorioCarrito.buscarPorUsuario(usuario.getId());

    // validacion

    assertNotNull(resultado);

    assertEquals(1, resultado.getItems().size());
  }

  //--------------METODOS AUXILIARES DE LOS TEST----------------

  public Usuario crearUsuarioValido() {
    Usuario usuario = new Usuario();
    usuario.setEmail("test@test.com");
    usuario.setPassword("123");
    usuario.setNombre("Test");
    usuario.setApellido("Test");
    usuario.setCelular(11543L);
    usuario.setDni(12345L);
    usuario.setRol("ADMIN");
    return usuario;
  }
}
