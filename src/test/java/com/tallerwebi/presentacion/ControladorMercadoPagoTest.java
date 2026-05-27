package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pagos.ServicioMercadoPago;
import com.tallerwebi.presentacion.Pagos.ControladorMercadoPago;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorMercadoPagoTest {

    private ServicioMercadoPago servicioMercadoPago;
    private ControladorMercadoPago controladorMercadoPago;

    @Before
    public void init() {
        this.servicioMercadoPago = mock(ServicioMercadoPago.class);
        this.controladorMercadoPago = new ControladorMercadoPago(this.servicioMercadoPago);
    }

    @Test
    public void alPagarDebeRedirigirAlCheckoutDeMercadoPago() {
        when(this.servicioMercadoPago.crearPreferenciaDePago())
                .thenReturn("https://www.mercadopago.com.ar/checkout/v1/redirect");

        ModelAndView modelAndView = this.controladorMercadoPago.pagar();

        assertThat(
                modelAndView.getViewName(),
                equalTo("redirect:https://www.mercadopago.com.ar/checkout/v1/redirect")
        );

    }
}