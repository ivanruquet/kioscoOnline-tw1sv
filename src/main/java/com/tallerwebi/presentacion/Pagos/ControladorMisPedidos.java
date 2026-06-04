package com.tallerwebi.presentacion.Pagos;

import com.tallerwebi.dominio.Pedidos.Pedido;
import com.tallerwebi.dominio.Pedidos.ServicioPedido;
import com.tallerwebi.dominio.Usuario.Usuario;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMisPedidos {

  private final ServicioPedido servicioPedido;

  @Autowired
  public ControladorMisPedidos(ServicioPedido servicioPedido) {
    this.servicioPedido = servicioPedido;
  }

  @RequestMapping(path = "/mis-pedidos", method = RequestMethod.GET)
  public ModelAndView verMisPedidos(HttpSession session) {
    Usuario usuario = (Usuario) session.getAttribute("USUARIO");

    if (usuario == null) {
      return new ModelAndView("redirect:/login");
    }

    List<Pedido> pedidos = servicioPedido.obtenerTodosLosPedidos(usuario.getId());

    ModelMap model = new ModelMap();
    model.put("usuario", usuario);
    model.put("pedidos", pedidos);
    return new ModelAndView("mis-pedidos", model);
  }
}
