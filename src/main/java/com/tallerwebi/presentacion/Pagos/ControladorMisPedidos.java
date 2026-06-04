package com.tallerwebi.presentacion.Pagos;

import com.tallerwebi.dominio.Carrito.ItemCarrito;
import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMisPedidos {

  @RequestMapping(path = "/mis-pedidos", method = RequestMethod.GET)
  public ModelAndView verMisPedidos(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");

    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }

    List<ItemCarrito> ultimoPedido = (List<ItemCarrito>) session.getAttribute("ultimoPedido"); //por ahora es ultimopedido, cuando se implemente la clase pedido, se modificara para que se vean todos
    ModelMap model = new ModelMap();
    model.put("usuario", usuario);
    model.put("ultimoPedido", ultimoPedido);
    return new ModelAndView("mis-pedidos", model);
  }
}
