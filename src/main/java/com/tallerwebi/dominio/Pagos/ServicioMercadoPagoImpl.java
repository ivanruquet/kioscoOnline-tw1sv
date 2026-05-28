package com.tallerwebi.dominio.Pagos;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.tallerwebi.dominio.Carrito.Carrito; // <-- CAMBIO
import com.tallerwebi.dominio.Carrito.ItemCarrito; // <-- CAMBIO
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {

  private static final Logger logger = LoggerFactory.getLogger(ServicioMercadoPagoImpl.class);

  private static final String TOKEN_MP = "TOKEN AQUI";

  @Override
  public String crearPreferenciaDePago(Carrito carrito) { // <-- CAMBIO
    MercadoPagoConfig.setAccessToken(TOKEN_MP);

    List<PreferenceItemRequest> itemsMercadoPago = new ArrayList<>();

    // Recorremos los ítems del modelo carrito
    for (ItemCarrito item : carrito.getItems()) {
      itemsMercadoPago.add(
        PreferenceItemRequest
          .builder()
          .title(item.getProducto().getNombre())
          .quantity(item.getCantidad())
          .unitPrice(BigDecimal.valueOf(item.getProducto().getPrecio()))
          .currencyId("ARS")
          .build()
      );
    }

    try {
      return new PreferenceClient()
        .create(PreferenceRequest.builder().items(itemsMercadoPago).build())
        .getInitPoint();
    } catch (Exception e) {
      logger.error("Error API Mercado Pago", e);
      return null;
    }
  }
}
