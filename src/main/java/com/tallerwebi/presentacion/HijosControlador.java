package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Hijos.Curso;
import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.ServicioHijo;
import com.tallerwebi.dominio.Usuario.Usuario;
import com.tallerwebi.dominio.excepcion.HijoExistenteException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    model.put("hijo", new Hijo()); // ← para el formulario oculto, agrego un hijo vacio

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

  @RequestMapping(path = "/guardarHijo", method = RequestMethod.POST)
  public ModelAndView guardarHijos(
    @ModelAttribute("hijo") Hijo hijo,
    @RequestParam String anio,
    @RequestParam String division,
    HttpSession session
  ) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESSION);

    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }
    try {
      String cursoNombre = obtenerNombreCurso(anio) + "_" + division;
      Curso curso = Curso.valueOf(cursoNombre);
      hijo.setCurso(curso);
      servicioHijo.guardarHijo(hijo, usuario);
    } catch (HijoExistenteException e) {
      ModelMap model = new ModelMap();
      model.put("error", "El hijo ya se encuentra registrado");
      return new ModelAndView("vistaHijos", model);
    }

    return new ModelAndView("redirect:/vistaHijos");
  }

  private String obtenerNombreCurso(String anio) {
    switch (anio) {
      case "1":
        return "PRIMERO";
      case "2":
        return "SEGUNDO";
      case "3":
        return "TERCERO";
      case "4":
        return "CUARTO";
      case "5":
        return "QUINTO";
      case "6":
        return "SEXTO";
      default:
        throw new IllegalArgumentException("Año inválido: " + anio);
    }
  }
}
