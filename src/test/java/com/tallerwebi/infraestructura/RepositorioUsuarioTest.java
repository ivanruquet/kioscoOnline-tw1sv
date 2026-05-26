package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tallerwebi.dominio.Usuario.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario.Usuario;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.hibernate.TransientObjectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateInfraestructuraTestConfig.class })
public class RepositorioUsuarioTest {

  @Autowired
  private SessionFactory sessionFactory;

  private RepositorioUsuario repositorioUsuario;

  @BeforeEach
  public void init() {
    repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaGuardarUnNuevoUsuario() {
    String emailNuevoUsuario = "nuevo.usuario@test.com";
    // preparacion
    Usuario usuario = this.dadoQueTengoUnUsuario(emailNuevoUsuario, "1234", "USER");

    // ejecucion
    this.cuandoGuardoUnUsuario(usuario);

    // validacion
    this.entoncesSeGuardoElUsuario(emailNuevoUsuario, usuario);
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaEncontrarUnUsuarioExistenteCuandoBuscoPorEmailYPassword() {
    String email = "test@test.com";
    String password = "123";
    Usuario usuario = this.dadoQueTengoUnUsuario(email, password, "USER");
    this.dadoQueExisteElUsuario(usuario);

    Usuario obtenido = this.cuandoBuscoUnUsuario(email, password);

    this.entoncesElUsuarioObtenidoEsCorrecto(obtenido, usuario);
  }

  @Test
  @Transactional
  public void noDeberiaEncontrarUnUsuarioInexistenteCuandoBuscoPorEmailYPassword() {
    Usuario obtenido = this.cuandoBuscoUnUsuario("test@test.com", "123");
    this.entoncesElUsuarioObtenidoEsNull(obtenido);
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaRetornarTrueSiExisteUsuarioConEseMail() {
    String email = "test@test.com";
    Usuario usuario = this.dadoQueTengoUnUsuario(email, "123", "USER");
    this.dadoQueExisteElUsuario(usuario);

    Boolean existe = this.cuandoVerificoSiExisteUsuarioPorMail(email);

    assertThat(existe, is(true));
  }

  @Test
  @Transactional
  public void deberiaRetornarFalseSiNoExisteUsuarioConEseMail() {
    Boolean existe = this.cuandoVerificoSiExisteUsuarioPorMail("noexiste@test.com");
    assertThat(existe, is(false));
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaRetornarTrueSiExisteUsuarioConEseDNI() {
    Usuario usuario = this.dadoQueTengoUnUsuario("test@test.com", "123", "USER");
    usuario.setDni(1234567890L);
    this.dadoQueExisteElUsuario(usuario);

    Boolean existe = this.cuandoVerificoSiExisteUsuarioPorDNI(1234567890L);

    assertThat(existe, is(true));
  }

  @Test
  @Transactional
  public void deberiaRetornarFalseSiNoExisteUsuarioConEseDNI() {
    Boolean existe = this.cuandoVerificoSiExisteUsuarioPorDNI(99999L);
    assertThat(existe, is(false));
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaModificarUnUsuarioExistente() {
    String email = "test@test.com";
    Usuario usuario = this.dadoQueTengoUnUsuario(email, "123", "USER");
    this.dadoQueExisteElUsuario(usuario);

    usuario.setEmail("nuevo@test.com");
    usuario.setCelular(12345678L);

    this.cuandoModificoUnUsuario(usuario);

    Usuario obtenido = this.cuandoObtengoUnUsuarioPorId(usuario.getId());
    this.entoncesElUsuarioObtenidoEsCorrecto(obtenido, usuario);
  }

  @Test
  @Transactional
  @Rollback
  public void deberiaLanzarUnaExcepcionAlIntentarModificarUnUsuarioInexistente() {
    Usuario usuario = this.dadoQueTengoUnUsuario("noexiste@test.com", "123", "USER");

    // Al no tener ID (no estar persistido), llamar a update debe lanzar la
    // excepción.
    this.entoncesSeLanzaUnaTransientObjectException(usuario);
  }

  private Boolean cuandoVerificoSiExisteUsuarioPorMail(String email) {
    return repositorioUsuario.existeUsuarioPorMail(email);
  }

  private Boolean cuandoVerificoSiExisteUsuarioPorDNI(Long dni) {
    return repositorioUsuario.existeUsuarioPorDni(dni);
  }

  private Usuario dadoQueTengoUnUsuario(String email, String password, String rol) {
    Usuario usuario = new Usuario();
    usuario.setEmail(email);
    usuario.setDni(1234567899L);
    usuario.setPassword(password);
    usuario.setRol(rol);
    usuario.setNombre("Test");
    usuario.setApellido("Usuario");
    usuario.setCelular(1234567890L);
    return usuario;
  }

  private Usuario cuandoObtengoUnUsuarioPorId(Long id) {
    return repositorioUsuario.buscarUsuarioPorId(id);
  }

  private void dadoQueExisteElUsuario(Usuario usuario) {
    this.sessionFactory.getCurrentSession().save(usuario);
  }

  private void cuandoGuardoUnUsuario(Usuario usuario) {
    repositorioUsuario.guardar(usuario);
  }

  private Usuario cuandoBuscoUnUsuario(String email, String password) {
    return repositorioUsuario.buscarUsuarioLogin(email, password);
  }

  private void cuandoModificoUnUsuario(Usuario usuario) {
    repositorioUsuario.modificar(usuario);
  }

  private void entoncesSeGuardoElUsuario(String email, Usuario usuarioEsperado) {
    String hql = "FROM Usuario WHERE email = :email";
    Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
    query.setParameter("email", email);
    Usuario usuarioObtenido = (Usuario) query.getSingleResult();
    this.entoncesElUsuarioObtenidoEsCorrecto(usuarioEsperado, usuarioObtenido);
  }

  private void entoncesElUsuarioObtenidoEsCorrecto(
    Usuario usuarioObtenido,
    Usuario usuarioEsperado
  ) {
    assertThat(usuarioObtenido.getEmail(), is(equalTo(usuarioEsperado.getEmail())));
    assertThat(usuarioObtenido.getPassword(), is(equalTo(usuarioEsperado.getPassword())));
    assertThat(usuarioObtenido.getActivo(), is(equalTo(usuarioEsperado.getActivo())));
    assertThat(usuarioObtenido.getRol(), is(equalTo(usuarioEsperado.getRol())));
  }

  private void entoncesElUsuarioObtenidoEsNull(Usuario obtenido) {
    assertThat(obtenido, is(nullValue()));
  }

  private void entoncesSeLanzaUnaTransientObjectException(Usuario usuario) {
    assertThrows(
      TransientObjectException.class,
      () -> {
        this.cuandoModificoUnUsuario(usuario);
      }
    );
  }
}
