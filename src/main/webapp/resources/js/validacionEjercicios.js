document.addEventListener("DOMContentLoaded", function () {
    console.log("Validation script loaded");

    // Define validation function in window scope
    window.validarFormularioEjercicio = function () {
        console.log("validarFormularioEjercicio function called!");

        const campos = [
            { id: "ejerciciosForm:nombre", nombre: "Nombre", minLength: 3, required: true },
            { id: "ejerciciosForm:descripcion", nombre: "Descripción", minLength: 10, required: true },
            { id: "ejerciciosForm:videoUrl", nombre: "Video URL", urlType: "video", required: false },
            { id: "ejerciciosForm:grupoMuscular_input", nombre: "Grupo Muscular", required: true }
        ];

        let esValido = true;

        campos.forEach(campo => {
            const input = document.getElementById(campo.id);

            if (!input) {
                console.error(`Element not found: ${campo.id}`);
                return;
            }

            const contenedor = input.closest("div") || input.parentElement;
            const valor = input.value.trim();

            let mensaje = "";
            let valido = true;

            // Validation logic
            if (campo.required && (!valor || valor === "" || valor === "null")) {
                mensaje = `El campo "${campo.nombre}" es obligatorio.`;
                valido = false;
            }
            else if (campo.minLength && valor.length < campo.minLength) {
                mensaje = `El campo "${campo.nombre}" debe tener al menos ${campo.minLength} caracteres.`;
                valido = false;
            }
            else if (campo.urlType === "video" && valor !== '') {
                const mp4Pattern = /\.mp4$/i;
                if (!mp4Pattern.test(valor)) {
                    mensaje = 'La URL del video debe terminar en .mp4';
                    valido = false;
                }
            }

            // Visual feedback
            input.style.border = valido ? "" : "2px solid red";

            // Error message display
            let msgEl = contenedor.querySelector(".error-msg");
            if (!msgEl) {
                msgEl = document.createElement("span");
                msgEl.className = "error-msg";
                msgEl.style.color = "red";
                msgEl.style.fontSize = "12px";
                msgEl.style.display = "block";
                msgEl.style.marginTop = "5px";
                contenedor.appendChild(msgEl);
            }

            msgEl.textContent = mensaje;
            msgEl.style.display = valido ? "none" : "block";

            if (!valido) {
                console.log(`Field ${campo.nombre} is invalid: "${mensaje}"`);
                esValido = false;
            }
        });

        // Show PrimeFaces growl message if validation fails
        if (!esValido) {
            console.log("Form is invalid, showing PrimeFaces messages");

            try {
                // Try to find the correct growl component
                if (window.PF) {
                    if (PF('msgs')) {
                        PF('msgs').renderMessage({
                            summary: "Error de validación",
                            detail: "Corrige los errores en el formulario de ejercicios.",
                            severity: "error"
                        });
                    }
                    else if (PF('mensajesDialogo')) {
                        PF('mensajesDialogo').renderMessage({
                            summary: "Error de validación",
                            detail: "Corrige los errores en el formulario de ejercicios.",
                            severity: "error"
                        });
                    }
                }
            } catch (e) {
                console.error("Error showing PrimeFaces messages:", e);
            }
        }

        console.log("Validation result:", esValido);
        return esValido;
    };
});