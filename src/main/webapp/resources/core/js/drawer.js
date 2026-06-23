document.addEventListener("DOMContentLoaded", function() {
  const btnHamburguesa = document.getElementById("btnHamburguesa");
  const panelCliente = document.getElementById("panelCliente");
  const drawerOverlay = document.getElementById("drawerOverlay");
  if (btnHamburguesa) {
    btnHamburguesa.addEventListener("click", function() {
      panelCliente.classList.add("drawer-open");
      drawerOverlay.classList.add("active");
    });
  }
  if (drawerOverlay) {
    drawerOverlay.addEventListener("click", function() {
      panelCliente.classList.remove("drawer-open");
      drawerOverlay.classList.remove("active");
    });
  }
});

function cerrarDrawer() {
  document.getElementById("panelCliente").classList.remove("drawer-open");
  document.getElementById("drawerOverlay").classList.remove("active");
}