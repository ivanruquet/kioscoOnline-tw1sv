const paso1 = document.getElementById("paso1");
const paso2 = document.getElementById("paso2");
const paso3 = document.getElementById("paso3");
const paso4 = document.getElementById("paso4");


const emailInput = document.getElementById("email-Rec");
const codigoInput = document.getElementById("codigo");
const nuevaClaveInput = document.getElementById("nuevaClave");

const btnEnviar = document.getElementById("btn-enviarCod");
const btnVerificar = document.getElementById("btn-verificarCod");
const btnCambiar = document.getElementById("btn-cambiarClave");

// Habilitar botón cuando hay celular
emailInput.addEventListener("keyup", () => {
    btnEnviar.disabled = emailInput.value.length === 0;
});
btnEnviar.addEventListener("click", async () => {
    const params =new URLSearchParams();
    params.append("email", emailInput.value);
    const res = await fetch('/spring/verificar-email', {
        method: 'POST',
        body: params
    });

    if (res.ok) {
        // email existe en la BD → avanzar al paso 2
        console.log("Código enviado: 1234");

        paso1.classList.add("oculto");
        paso2.classList.remove("oculto");
    } else {
        // email no existe
        alert("El email no está registrado");
        return;
    }
});

// Habilitar botón cuando hay código
codigoInput.addEventListener("keyup", () => {
    btnVerificar.disabled = codigoInput.value.length === 0;
});

// Paso 2 → Paso 3
btnVerificar.addEventListener("click", () => {
    // por ahora validamos contra el código hardcodeado
    if (codigoInput.value === "1234") {
        paso2.classList.add("oculto");
        paso3.classList.remove("oculto");
    } else {
        alert("Código incorrecto");
    }
});

// Habilitar botón cuando hay nueva clave
nuevaClaveInput.addEventListener("keyup", () => {
    btnCambiar.disabled = nuevaClaveInput.value.length === 0;
});

btnCambiar.addEventListener("click", async () => {
    const params = new URLSearchParams();
    params.append("email", emailInput.value);
    params.append("nuevaClave", nuevaClaveInput.value);

    const res = await fetch('/spring/actualizar-contrasenia', {
        method: 'POST',
        body: params
    });

    if (res.ok) {
        paso3.classList.add("oculto");
        paso4.classList.remove("oculto");
    }
});