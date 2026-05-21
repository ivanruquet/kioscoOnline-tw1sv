package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.ServicioHijo;
import com.tallerwebi.dominio.Usuario;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PerfilControlador {

  private final ServicioHijo servicioHijo;
  private static final String VISTA_PERFIL = "perfil";

  @Autowired
  public PerfilControlador(ServicioHijo servicioHijo) {
    this.servicioHijo = servicioHijo;
  }

  @RequestMapping(path = "/perfil", method = RequestMethod.GET)
  public ModelAndView irAlPerfil(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");
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

    mv.setViewName(VISTA_PERFIL);
    mv.addAllObjects(model);
    return mv;
  }

  public ModelAndView mostrarDatosUsuario(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usuario", usuario);
    mv.setViewName("perfil");
    return mv;
  }
}
