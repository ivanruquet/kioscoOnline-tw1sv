package com.tallerwebi.dominio.HijoTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.RepositorioHijo;
import com.tallerwebi.dominio.Hijos.ServicioHijo;
import com.tallerwebi.dominio.Hijos.ServicioHijoImpl;
import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ServicioHijoTest {

  private Usuario usuarioMock;
  private ServicioHijo servicioHijo;
  private RepositorioHijo repositorioHijoMock;
  private Hijo hijoMock;

  @BeforeEach
  public void init() {
    repositorioHijoMock = Mockito.mock(RepositorioHijo.class);

    usuarioMock = Mockito.mock(Usuario.class);
    servicioHijo = new ServicioHijoImpl(repositorioHijoMock);
    hijoMock = Mockito.mock(Hijo.class);
  }

  @Test
  public void cuandoSeSoliciteHijosDebeBuscarEnElRepoYDevolverList() {
    List<Hijo> listaHijoSimulada = Arrays.asList(hijoMock);
    when(repositorioHijoMock.listarHijos(usuarioMock.getId())).thenReturn(listaHijoSimulada);

    List<Hijo> listaHijoObtenida = servicioHijo.obtenerHijosPorUsuario(usuarioMock.getId());
    assertThat(listaHijoObtenida, hasSize(1));
  }
}
