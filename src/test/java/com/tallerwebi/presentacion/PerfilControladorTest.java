package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.Usuario;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

public class PerfilControladorTest {

    private Usuario usuarioMock;
    private PerfilControlador perfilControlador;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {
        perfilControlador = new PerfilControlador();
        usuarioMock = Mockito.mock(Usuario.class);
        sessionMock = Mockito.mock(HttpSession.class);
    }

    @Test
    public void siNoHayUsuarioRegistradoAlApretarPerfilIrAlLogin() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(null);
        ModelAndView modelAndView = perfilControlador.irAlPerfil(sessionMock);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void siHayUsuarioRegistradoAlApretarPerfilIrAlPerfil() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        ModelAndView modelAndView = perfilControlador.irAlPerfil(sessionMock);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("perfil"));
    }

    @Test
    public void elPerfilDebeMostrarNombreDeUsuario() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(usuarioMock.getNombre()).thenReturn("nombrePrueba");
        ModelAndView modelAndView = perfilControlador.mostrarDatosUsuario(sessionMock);
        assertThat(((Usuario) modelAndView.getModel().get("usuario")).getNombre(),
                equalToIgnoringCase("nombrePrueba"));
    }

    @Test
    public void elPerfilDebeMostrarApellidoDeUsuario() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(usuarioMock.getApellido()).thenReturn("apellidoPrueba");
        ModelAndView modelAndView = perfilControlador.mostrarDatosUsuario(sessionMock);
        assertThat(((Usuario) modelAndView.getModel().get("usuario")).getApellido(),
                equalToIgnoringCase("apellidoPrueba"));
    }

    @Test
    public void elPerfilDebeMostrarEmailDeUsuario() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(usuarioMock.getEmail()).thenReturn("email");
        ModelAndView modelAndView = perfilControlador.mostrarDatosUsuario(sessionMock);
        assertThat(((Usuario) modelAndView.getModel().get("usuario")).getEmail(),
                equalToIgnoringCase("email"));
    }

    @Test
    public void elPerfilDebeMostrarFotoDePerfilDeUsuario() {
        when(sessionMock.getAttribute("USUARIO")).thenReturn(usuarioMock);
        when(usuarioMock.getFotoPerfil()).thenReturn("foto.jpg");
        ModelAndView modelAndView = perfilControlador.mostrarDatosUsuario(sessionMock);
        assertThat(((Usuario) modelAndView.getModel().get("usuario")).getFotoPerfil(),
                equalToIgnoringCase("foto.jpg"));
    }
}