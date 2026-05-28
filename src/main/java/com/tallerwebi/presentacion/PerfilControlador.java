package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.ServicioHijo;
import com.tallerwebi.dominio.Usuario.ServicioUsuario;
import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PerfilControlador {

  private final ServicioHijo servicioHijo;
  private final ServicioUsuario servicioUsuario;
  private static final String VISTA_PERFIL = "perfil";
  private static final String VISTA_HIJOS = "vistaHijos";
  private static final String USUARIO_SESSION = "USUARIO";

  @Autowired
  public PerfilControlador(ServicioHijo servicioHijo, ServicioUsuario servicioUsuario) {
    this.servicioHijo = servicioHijo;
    this.servicioUsuario = servicioUsuario;
  }

  @RequestMapping(path = "/perfil", method = RequestMethod.GET)
  public ModelAndView irAlPerfil(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESSION);
    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }
    ModelMap model = new ModelMap();
    ModelAndView mv = new ModelAndView();
    model.put("usuario", usuario);

    mv.setViewName(VISTA_PERFIL);
    mv.addAllObjects(model);
    return mv;
  }

  @RequestMapping(path = "/vistaHijos", method = RequestMethod.GET)
  public ModelAndView irAvistaHijos(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESSION);
    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }
    ModelMap model = new ModelMap();
    ModelAndView mv = new ModelAndView();
    model.put("usuario", usuario);

    List<Hijo> hijos = this.servicioHijo.obtenerHijosPorUsuario(usuario.getId());
    if (hijos == null || hijos.isEmpty()) {
      model.put("mensajeError", "Aún no tenés hijos registrados");
    } else {
      model.put("hijos", hijos);
    }
    mv.setViewName(VISTA_HIJOS);
    mv.addAllObjects(model);
    return mv;
  }

  public ModelAndView mostrarDatosUsuario(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESSION);
    ModelAndView mv = new ModelAndView();
    mv.addObject("usuario", usuario);
    mv.setViewName(VISTA_PERFIL);
    return mv;
  }

  @RequestMapping(path = "/editarPerfil", method = RequestMethod.POST)
  public ModelAndView editarPerfil(
    @RequestParam(required = false) String email,
    @RequestParam(required = false) String celular,
    @RequestParam(required = false) String fotoPerfil,
    HttpSession session
  ) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESSION);
    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }
    if (email != null && !email.isEmpty()) {
      servicioUsuario.actualizarMail(usuario.getId(), email);
    }
    if (celular != null && !celular.isEmpty()) {
      Long celularLong = Long.parseLong(celular);
      servicioUsuario.actualizarCelular(usuario.getId(), celularLong);
    }
    if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
      servicioUsuario.actualizarFoto(usuario.getId(), fotoPerfil);
    }
    //refresco el usuario con los datos actualizados
    Usuario usuarioActualizado = servicioUsuario.buscarPorId(usuario.getId());
    session.setAttribute(USUARIO_SESSION, usuarioActualizado);

    return new ModelAndView("redirect:/perfil");
  }
}
