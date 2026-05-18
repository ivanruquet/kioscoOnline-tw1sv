package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PerfilControlador {

  @RequestMapping(path = "/perfil", method = RequestMethod.GET)
  public ModelAndView irAlPerfil(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");
    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }
    ModelAndView mv = new ModelAndView();
    mv.addObject("usuario", usuario);
    mv.setViewName("perfil");
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
