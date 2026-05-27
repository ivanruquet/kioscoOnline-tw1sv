package com.tallerwebi.dominio.Pagos;

import org.springframework.stereotype.Service;

@Service
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {

    @Override
    public String crearPreferenciaDePago() {
        return "https://www.mercadopago.com.ar/checkout/v1/redirect";
    }
}