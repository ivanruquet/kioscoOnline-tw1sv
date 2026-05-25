package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import com.tallerwebi.dominio.Productos.CategoriaProductos;
import com.tallerwebi.dominio.Productos.Producto;
import com.tallerwebi.dominio.Productos.RepositorioProducto;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import java.util.List;
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
public class RepositorioProductoTest {

  @Autowired
  private SessionFactory sessionFactory;

  private RepositorioProducto repositorioProducto;

  @BeforeEach
  public void init() {
    repositorioProducto = new RepositorioProductoImpl(sessionFactory);
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaRetornarTodosLosProductosCargadosEnLaBD() {
    Producto producto = this.dadoQueTengoUnProducto();

    Producto producto2 = this.dadoQueTengoUnProducto2();

    this.sessionFactory.getCurrentSession().save(producto);
    this.sessionFactory.getCurrentSession().save(producto2);

    List<Producto> listaProductos = repositorioProducto.listarProductos();

    assertThat(listaProductos, hasSize(2));
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaRetornarTodosLosProductos_FiltradoPorCategoria_CargadosEnLaBD() {
    String categoria = "categoria";

    Producto producto = this.dadoQueTengoUnProducto();

    Producto producto2 = this.dadoQueTengoUnProducto2();

    this.sessionFactory.getCurrentSession().save(producto);
    this.sessionFactory.getCurrentSession().save(producto2);

    List<Producto> listaProductos = repositorioProducto.listarProductosFiltrados(categoria);

    assertThat(listaProductos, hasSize(1));
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaRetornarTodasLasCategoriasEnLaBD() {
    CategoriaProductos categoria1 = this.dadoQueTengoUnaCategoria();
    CategoriaProductos categoria2 = this.dadoQueTengoOtraCategoria();

    List<CategoriaProductos> listaCategorias = repositorioProducto.listarCategorias();

    assertThat(listaCategorias, hasSize(2));
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaRetornarUnProductoPorSuNombreAlBuscarlo() {
    String busqueda = "1";
    Producto producto = this.dadoQueTengoUnProducto();
    Producto producto2 = this.dadoQueTengoUnProducto2();
    this.sessionFactory.getCurrentSession().save(producto);
    this.sessionFactory.getCurrentSession().save(producto2);

    List<Producto> productoBuscado = repositorioProducto.buscarProductos(busqueda);

    assertThat(productoBuscado, hasSize(1));
  }

  //--------------METODOS AUXILIARES DE LOS TEST----------------

  public CategoriaProductos dadoQueTengoUnaCategoria() {
    CategoriaProductos categoria = new CategoriaProductos();
    categoria.setId(9L);
    categoria.setNombreCategoria("categoria");
    this.sessionFactory.getCurrentSession().save(categoria);
    return categoria;
  }

  public CategoriaProductos dadoQueTengoOtraCategoria() {
    CategoriaProductos categoria2 = new CategoriaProductos();
    categoria2.setId(8L);
    categoria2.setNombreCategoria("categoria2");
    this.sessionFactory.getCurrentSession().save(categoria2);
    return categoria2;
  }

  public Producto dadoQueTengoUnProducto() {
    CategoriaProductos categoria = this.dadoQueTengoUnaCategoria();

    Producto producto = new Producto();
    producto.setNombre("Producto1");
    producto.setPrecio(100.0);
    producto.setCantidad(5);
    producto.setCategoria(categoria);
    return producto;
  }

  public Producto dadoQueTengoUnProducto2() {
    CategoriaProductos categoria2 = this.dadoQueTengoOtraCategoria();

    Producto producto2 = new Producto();
    producto2.setNombre("Producto2");
    producto2.setPrecio(100.0);
    producto2.setCantidad(5);
    producto2.setCategoria(categoria2);
    return producto2;
  }
}
