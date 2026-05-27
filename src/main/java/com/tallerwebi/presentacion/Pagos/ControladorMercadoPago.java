package com.tallerwebi.presentacion.Pagos;

import com.tallerwebi.dominio.Pagos.ServicioMercadoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMercadoPago {

    private final ServicioMercadoPago servicioMercadoPago;

    @Autowired
    public ControladorMercadoPago(
            ServicioMercadoPago servicioMercadoPago
    ) {
        this.servicioMercadoPago = servicioMercadoPago;
    }

    @RequestMapping(
            path = "/pagar",
            method = RequestMethod.GET
    )
    public ModelAndView pagar() {

        String urlCheckout =
                this.servicioMercadoPago.crearPreferenciaDePago();

        return new ModelAndView(
                "redirect:" + urlCheckout
        );
    }
}