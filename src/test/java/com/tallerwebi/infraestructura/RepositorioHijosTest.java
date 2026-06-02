package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.tallerwebi.dominio.Hijos.Curso;
import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.RepositorioHijo;
import com.tallerwebi.dominio.Usuario.DatosPersonales;
import com.tallerwebi.dominio.Usuario.Usuario;
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
public class RepositorioHijosTest {

  @Autowired
  private SessionFactory sessionFactory;

  private RepositorioHijo repositorioHijo;

  @BeforeEach
  public void init() {
    repositorioHijo = new RepositorioHijoImpl(sessionFactory);
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaListarLosHijosDeUnUsuario() {
    Usuario padre = dadoQueExisteUnPadre();

    dadoQueExisteUnHijo("Santi", 12345L, padre);
    dadoQueExisteUnHijo("Mora", 123545L, padre);

    List<Hijo> hijos = repositorioHijo.listarHijos(padre.getId());

    assertThat(hijos, hasSize(2));
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaBuscarHijoPorId() {
    Usuario padre = dadoQueExisteUnPadre();

    Hijo hijo = dadoQueExisteUnHijo("Santiago", 12345L, padre);

    Hijo obtenido = repositorioHijo.buscarPorId(hijo.getId());

    assertThat(obtenido, notNullValue());
    assertThat(obtenido.getNombre(), equalTo("Santiago"));
  }

  @Test
  @Transactional
  public void deberiaRetornarNullSiElHijoNoExiste() {
    Hijo hijo = repositorioHijo.buscarPorId(999L);

    assertThat(hijo, nullValue());
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaRetornarTrueSiExisteHijoConEseDni() {
    Usuario padre = dadoQueExisteUnPadre();

    Hijo hijo = dadoQueExisteUnHijo("Santiago", 12345L, padre);

    Boolean existe = repositorioHijo.existeHijoPorDni(12345L);

    assertThat(existe, is(true));
  }

  @Test
  @Transactional
  public void deberiaRetornarFalseSiNoExisteHijoConEseDni() {
    Boolean existe = repositorioHijo.existeHijoPorDni(99999L);

    assertThat(existe, is(false));
  }

  private Hijo dadoQueExisteUnHijo(String nombre, Long dni, Usuario padre) {
    Hijo hijo = new Hijo();
    hijo.setNombre(nombre);
    hijo.setDni(dni);
    hijo.setPadre(padre);
    hijo.setApellido(padre.getApellido());
    hijo.setCurso(Curso.CUARTO_D);

    sessionFactory.getCurrentSession().save(hijo);

    return hijo;
  }

  private Usuario dadoQueExisteUnPadre() {
    DatosPersonales datos = new DatosPersonales();
    datos.setNombre("Juan");
    datos.setApellido("Perez");
    datos.setDni(12345678L);
    datos.setCelular(1122334455L);

    Usuario padre = new Usuario();
    padre.setDatosPersonales(datos);

    padre.setEmail("padre@test.com");
    padre.setPassword("1234");
    padre.setRol("USER");

    sessionFactory.getCurrentSession().save(padre);

    return padre;
  }
}
