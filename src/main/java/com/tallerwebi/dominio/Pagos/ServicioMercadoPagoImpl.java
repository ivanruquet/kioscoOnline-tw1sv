package com.tallerwebi.dominio.Pagos;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.tallerwebi.dominio.Carrito.Carrito;
import com.tallerwebi.dominio.Carrito.ItemCarrito;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {

  private static final Logger logger = LoggerFactory.getLogger(ServicioMercadoPagoImpl.class);

  // Dejamos tu token real para que siga funcionando la pasarela
  private static final String TOKEN_MP =
    "APP_USR-4635339104323317-052622-65723bd2783dedac7f79f3df036e1846-3430140886";

  @Override
  public String crearPreferenciaDePago(Carrito carrito) {
    if (carrito == null || carrito.getItems() == null) {
      return null;
    }

    try {
      MercadoPagoConfig.setAccessToken(TOKEN_MP);

      List<PreferenceItemRequest> itemsMercadoPago = new ArrayList<>();

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

      return new PreferenceClient()
        .create(PreferenceRequest.builder().items(itemsMercadoPago).build())
        .getInitPoint();
    } catch (Exception e) {
      logger.error("Error API Mercado Pago", e);
      return null;
    }
  }
}
