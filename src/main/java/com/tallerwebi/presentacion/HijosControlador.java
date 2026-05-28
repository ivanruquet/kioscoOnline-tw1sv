package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.ServicioHijo;
import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HijosControlador {

  ServicioHijo servicioHijo;
  private static final String VISTA_HIJOS = "vistaHijos";
  private static final String USUARIO_SESSION = "USUARIO";
  private static final String USUARIO_MODEL = "usuario";

  public HijosControlador(ServicioHijo servicioHijo) {
    this.servicioHijo = servicioHijo;
  }

  @RequestMapping(path = "/vistaHijos", method = RequestMethod.GET)
  public ModelAndView irAvistaHijos(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESSION);
    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }
    ModelMap model = new ModelMap();
    ModelAndView mv = new ModelAndView();
    model.put(USUARIO_MODEL, usuario);

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
}
