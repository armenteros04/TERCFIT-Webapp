document.addEventListener("DOMContentLoaded", function () {
    const formFields = {
        nombre: document.getElementById("recetasForm:nombre"),
        ingredientes: document.getElementById("recetasForm:ingredientes"),
        pasos: document.getElementById("recetasForm:pasos"),
        calorias: document.getElementById("recetasForm:calorias"),
        proteina: document.getElementById("recetasForm:proteina"),
        grasa: document.getElementById("recetasForm:grasa"),
        carbohidratos: document.getElementById("recetasForm:carbohidratos"),
        fibra: document.getElementById("recetasForm:fibra"),
        imagen: document.getElementById("recetasForm:imagen")
    };

    window.validarFormularioReceta = function () {
        const campos = [
            { id: "recetasForm:nombre", nombre: "Nombre" },
            { id: "recetasForm:ingredientes", nombre: "Ingredientes" },
            { id: "recetasForm:pasos", nombre: "Pasos" },
            { id: "recetasForm:calorias", nombre: "Calorías", numerico: true },
            { id: "recetasForm:proteina", nombre: "Proteína", numerico: true },
            { id: "recetasForm:grasa", nombre: "Grasa", numerico: true },
            { id: "recetasForm:carbohidratos", nombre: "Carbohidratos", numerico: true },
            { id: "recetasForm:fibra", nombre: "Fibra", numerico: true },
            { id: "recetasForm:imagen", nombre: "Imagen" }
        ];

        let esValido = true;

        campos.forEach(campo => {
            const input = document.getElementById(campo.id);
            const contenedor = input.closest("div") || input.parentElement;
            const valor = input?.value.trim() ?? "";

            let mensaje = "";
            let valido = true;

            if (!valor) {
                mensaje = `El campo "${campo.nombre}" es obligatorio.`;
                valido = false;
            } else if (campo.numerico) {
                const numero = Number(valor);
                if (!/^\d+(\.\d+)?$/.test(valor) || isNaN(numero) || numero < 0) {
                    mensaje = `El campo "${campo.nombre}" debe ser un número positivo.`;
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

        return esValido;
    };

    // Validación en tiempo real en inputs de la tabla
    document.querySelectorAll("table[id$='recetasTable'] input").forEach(input => {
        input.addEventListener("input", function () {
            const valor = input.value.trim();
            const nombreCampo = input.getAttribute("name") || input.id;

            let valido = true;
            let mensaje = "";

            if (nombreCampo.includes("calorias") || nombreCampo.includes("proteina") ||
                nombreCampo.includes("grasa") || nombreCampo.includes("carbohidratos") ||
                nombreCampo.includes("fibra")) {
                const numero = parseFloat(valor);
                valido = valor !== "" && !isNaN(numero) && numero >= 0;
                mensaje = "Debe ser un número no negativo.";
            } else if (nombreCampo.includes("ingredientes")) {
                valido = /^([^,]+,)*[^,]+$/.test(valor);
                mensaje = "Debe separar los ingredientes por comas.";
            } else {
                valido = valor !== "";
                mensaje = "Este campo es obligatorio.";
            }

            input.setCustomValidity(valido ? "" : mensaje);
            input.reportValidity();
        });
    });

    // Validación al hacer clic en el botón de "check" del rowEditor
    document.querySelectorAll(".ui-row-editor .ui-icon-check").forEach(function (checkBtn) {
        checkBtn.addEventListener("click", function (e) {
            const fila = this.closest("tr");

            if (!validarEdicionReceta(fila)) {
                e.preventDefault();
                e.stopImmediatePropagation();
                return false;
            }
        });
    });

    function validarEdicionReceta(fila) {
        const inputs = fila.querySelectorAll("input");
        let valido = true;

        inputs.forEach((input) => {
            const valor = input.value.trim();
            const nombre = input.name || input.id;
            const esNumerico = /(calorias|proteina|grasa|carbohidratos|fibra)/i.test(nombre);

            let mensaje = "";

            if (valor === "") {
                mensaje = "Este campo es obligatorio.";
                valido = false;
            } else if (esNumerico) {
                const num = parseFloat(valor);
                if (isNaN(num) || num < 0) {
                    mensaje = "Debe ser un número positivo.";
                    valido = false;
                }
            }

            if (mensaje !== "") {
                input.classList.add("ui-state-error");
                input.setCustomValidity(mensaje);
            } else {
                input.classList.remove("ui-state-error");
                input.setCustomValidity("");
            }

            input.reportValidity();
        });

        if (!valido && window.PF && PF('msgs')) {
            PF('msgs').show({
                summary: "Error de validación",
                detail: "Corrige los errores antes de guardar.",
                severity: "error",
            });
        }

        return valido;
    }

    // Validación en tiempo real en formulario principal
    Object.values(formFields).forEach(campo => {
        campo.addEventListener("input", window.validarFormularioReceta);
    });

    window.validarFormularioReceta(); // Validación inicial
});
