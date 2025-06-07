document.addEventListener("DOMContentLoaded", function () {
    const biometriaFields = {
        peso: document.getElementById("biometriaForm:peso"),
        altura: document.getElementById("biometriaForm:altura"),
        edad: document.getElementById("biometriaForm:edad"),
        suenio: document.getElementById("biometriaForm:suenio"),
        calorias: document.getElementById("biometriaForm:calorias"),
        agua: document.getElementById("biometriaForm:agua")
    };

    window.validarFormularioBiometria = function () {
        const campos = [
            { id: "biometriaForm:peso", nombre: "Peso", numerico: true },
            { id: "biometriaForm:altura", nombre: "Altura", numerico: true },
            { id: "biometriaForm:edad", nombre: "Edad", numerico: true },
            { id: "biometriaForm:suenio", nombre: "Sueño", numerico: true, rango: [0, 24] },
            { id: "biometriaForm:calorias", nombre: "Calorías", numerico: true },
            { id: "biometriaForm:agua", nombre: "Agua", numerico: true }
        ];

        let esValido = true;

        campos.forEach(campo => {
            const input = document.getElementById(campo.id);
            const contenedor = input.closest("div") || input.parentElement;
            const valor = input?.value.trim() ?? "";

            let mensaje = "";
            let valido = true;

            if (!valor || valor === "0") {
                mensaje = `El campo "${campo.nombre}" no puede estar vacío ni ser cero.`;
                valido = false;
            } else if (campo.numerico) {
                const numero = Number(valor);
                if (!/^\d+(\.\d+)?$/.test(valor) || isNaN(numero) || numero <= 0) {
                    mensaje = `El campo "${campo.nombre}" debe ser un número positivo.`;
                    valido = false;
                } else if (campo.rango && (numero < campo.rango[0] || numero > campo.rango[1])) {
                    mensaje = `El campo "${campo.nombre}" debe estar entre ${campo.rango[0]} y ${campo.rango[1]}.`;
                    valido = false;
                }
            }

            input.style.border = valido ? "" : "2px solid red";

            let msgEl = contenedor.querySelector(".error-msg");
            if (!msgEl) {
                msgEl = document.createElement("span");
                msgEl.className = "error-msg";
                msgEl.style.color = "red";
                msgEl.style.fontSize = "12px";
                contenedor.appendChild(msgEl);
            }
            msgEl.textContent = valido ? "" : mensaje;

            if (!valido) esValido = false;
        });

        if (!esValido && window.PF && PF('growl')) {
            PF('growl').renderMessage({
                summary: "Error de validación",
                detail: "Corrige los errores en los campos biométricos.",
                severity: "error"
            });
        }

        return esValido;
    };

    Object.values(biometriaFields).forEach(campo => {
        campo.addEventListener("input", window.validarFormularioBiometria);
    });

    window.validarFormularioBiometria(); // Validación inicial
});
