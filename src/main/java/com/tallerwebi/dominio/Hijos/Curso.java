package com.tallerwebi.dominio.Hijos;

public enum Curso {
  PRIMERO_A("1°A"),
  PRIMERO_B("1°B"),
  PRIMERO_C("1°C"),
  PRIMERO_D("1°D"),
  SEGUNDO_A("2°A"),
  SEGUNDO_B("2°B"),
  SEGUNDO_C("2°C"),
  SEGUNDO_D("2°D"),
  TERCERO_A("3°A"),
  TERCERO_B("3°B"),
  TERCERO_C("3°C"),
  TERCERO_D("3°D"),
  CUARTO_A("4°A"),
  CUARTO_B("4°B"),
  CUARTO_C("4°C"),
  CUARTO_D("4°D"),
  QUINTO_A("5°A"),
  QUINTO_B("5°B"),
  QUINTO_C("5°C"),
  QUINTO_D("5°D"),
  SEXTO_A("6°A"),
  SEXTO_B("6°B"),
  SEXTO_C("6°C"),
  SEXTO_D("6°D");

  private final String descripcion;

  Curso(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getAnio() {
    return descripcion.substring(0, 2); // devuelve "1°", "2°", etc.
  }

  public String getDivision() {
    return descripcion.substring(2); // devuelve "A", "B", etc.
  }
}
