package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaHijos extends VistaWeb {

  public VistaHijos(Page page) {
    super(page);
    page.navigate("localhost:8080/spring/vistaHijos");
  }

  public void darClickEnAgregarHijo() {
    darClickEnElElemento("#btnAbrirForm");
  }

  public void escribirNombre(String nombre) {
    escribirEnElElemento("input[name='nombre']", nombre);
  }

  public void escribirApellido(String apellido) {
    escribirEnElElemento("input[name='apellido']", apellido);
  }

  public void escribirDni(String dni) {
    escribirEnElElemento("input[name='dni']", dni);
  }

  public void escribirFechaNacimiento(String fecha) {
    escribirEnElElemento("input[name='fechaNac']", fecha);
  }

  public void seleccionarAnio(String anio) {
    seleccionarOpcion("select[name='anio']", anio);
  }

  public void seleccionarDivision(String division) {
    seleccionarOpcion("select[name='division']", division);
  }

  public void darClickEnGuardar() {
    darClickEnElElemento("button.btn-guardar");
  }

  public String obtenerNombreDelPrimerHijo() {
    return obtenerTextoDelElemento(".hijo-nombre");
  }
}
