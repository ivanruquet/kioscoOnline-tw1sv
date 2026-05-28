package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Hijos.Hijo;
import com.tallerwebi.dominio.Hijos.ServicioHijo;
import com.tallerwebi.dominio.Usuario.ServicioUsuario;
import com.tallerwebi.dominio.Usuario.Usuario;
import com.tallerwebi.dominio.excepcion.NoSePudoGuardarInformacionException;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PerfilControlador {

  private final ServicioHijo servicioHijo;
  private final ServicioUsuario servicioUsuario;
  private static final String VISTA_PERFIL = "perfil";
  private static final String VISTA_HIJOS = "vistaHijos";
  private static final String USUARIO_SESSION = "USUARIO";
  private static final String USUARIO_MODEL = "usuario";

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
    model.put(USUARIO_MODEL, usuario);

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

  public ModelAndView mostrarDatosUsuario(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESSION);
    ModelAndView mv = new ModelAndView();
    mv.addObject(USUARIO_MODEL, usuario);
    mv.setViewName(VISTA_PERFIL);
    return mv;
  }

  @RequestMapping(path = "/editarPerfil", method = RequestMethod.POST)
  public ModelAndView editarPerfil(
    @Valid DatosEditarPerfilDTO datosEditarPerfil,
    BindingResult bindingResult,
    HttpSession session
  ) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESSION);
    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }
    if (bindingResult.hasErrors()) {
      return devolverVistaError(usuario, "Hay campos inválidos");
    }

    //refresco el usuario con los datos actualizados
    ModelAndView errorMail = editarMail(usuario, datosEditarPerfil.getEmail());
    if (errorMail != null) {
      return errorMail;
    }

    ModelAndView errorCelular = editarCelular(usuario, datosEditarPerfil.getCelular());
    if (errorCelular != null) {
      return errorCelular;
    }

    ModelAndView errorFoto = editarFoto(usuario, datosEditarPerfil.getFotoPerfil());
    if (errorFoto != null) {
      return errorFoto;
    }
    Usuario usuarioActualizado = servicioUsuario.buscarPorId(usuario.getId());
    session.setAttribute(USUARIO_SESSION, usuarioActualizado);

    return new ModelAndView("redirect:/perfil");
  }

  private ModelAndView editarFoto(Usuario usuario, MultipartFile fotoPerfil) {
    if (fotoPerfil == null || fotoPerfil.isEmpty()) {
      return null;
    }

    try {
      servicioUsuario.actualizarFoto(usuario.getId(), fotoPerfil);
      return null;
    } catch (NoSePudoGuardarInformacionException e) {
      return devolverVistaError(usuario, e.getMessage());
    }
  }

  private ModelAndView editarCelular(Usuario usuario, String celular) {
    if (celular == null || celular.isEmpty()) {
      return null;
    }

    try {
      Long celularLong = Long.parseLong(celular);
      servicioUsuario.actualizarCelular(usuario.getId(), celularLong);
      return null;
    } catch (NumberFormatException e) {
      return devolverVistaError(usuario, "El celular debe contener solo números");
    } catch (NoSePudoGuardarInformacionException e) {
      return devolverVistaError(usuario, e.getMessage());
    }
  }

  private ModelAndView editarMail(Usuario usuario, String email) {
    if (email == null || email.isEmpty()) {
      return null;
    }

    try {
      servicioUsuario.actualizarMail(usuario.getId(), email);
      return null;
    } catch (NoSePudoGuardarInformacionException e) {
      return devolverVistaError(usuario, e.getMessage());
    }
  }

  private ModelAndView devolverVistaError(Usuario usuario, String mensaje) {
    ModelMap modelo = new ModelMap();
    modelo.put(USUARIO_MODEL, usuario);
    modelo.put("mensajeError", mensaje);

    return new ModelAndView(VISTA_PERFIL, modelo);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(
    org.springframework.web.multipart.MaxUploadSizeExceededException.class
  )
  public ModelAndView manejarErrorArchivoMuyGrande(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute(USUARIO_SESSION);

    return devolverVistaError(usuario, "La imagen supera el tamaño máximo permitido de 5MB");
  }
}
