package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

public class HomeControlador {

  public ModelAndView mostrarDatosHome(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usuario", usuario);

    mv.setViewName("home");
    return mv;
  }
}
