package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaCarrito extends VistaWeb {

  public VistaCarrito(Page page) {
    super(page);
  }

  public void confirmarPedido() {
    this.darClickEnElElemento(".btn-confirmar");
  }
}
