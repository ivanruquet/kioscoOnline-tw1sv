package com.tallerwebi.presentacion.DistribucionCarrito;

public class ItemDistribucionDTO {

  private Long productoId;
  private Long hijoId;
  private Integer cantidad;

  public ItemDistribucionDTO() {}

  public ItemDistribucionDTO(long productoId, long hijoId, Integer cantidad) {
    this.productoId = productoId;
    this.hijoId = hijoId;
    this.cantidad = cantidad;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public Long getHijoId() {
    return hijoId;
  }

  public void setHijoId(Long hijoId) {
    this.hijoId = hijoId;
  }

  public Long getProductoId() {
    return productoId;
  }

  public void setProductoId(Long productoId) {
    this.productoId = productoId;
  }
}
