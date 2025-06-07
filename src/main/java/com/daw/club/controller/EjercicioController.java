package com.daw.club.controller;

import com.daw.club.model.Cliente;
import com.daw.club.model.dao.ClienteDAOJPA;
import com.daw.club.model.service.EjercicioService;
import com.daw.club.qualifiers.DAOJpa;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import com.daw.club.model.Ejercicio;
import com.daw.club.model.GrupoMuscular;
import jakarta.security.enterprise.SecurityContext;

@Named("ejercicioController")
@ViewScoped
public class EjercicioController implements Serializable {

    private static final Logger logger = Logger.getLogger(EjercicioController.class.getName());

    @Inject
    private EjercicioService ejercicioService;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private FacesContext facesContext;

    @Inject @DAOJpa
    private ClienteDAOJPA clienteDAOJPA;


    private List<Ejercicio> ejercicios;
    private Ejercicio ejercicioSeleccionado;
    private Ejercicio nuevoEjercicio = new Ejercicio("", "", "", GrupoMuscular.NULL);

    public void cargarEjercicios() {
        ejercicios = ejercicioService.obtenerTodos();
    }

    public List<Ejercicio> getEjercicios() {
        if (ejercicios == null) {
            cargarEjercicios();
        }
        return ejercicios;
    }

    public Ejercicio getEjercicioSeleccionado() {
        return ejercicioSeleccionado;
    }

    public void setEjercicioSeleccionado(Ejercicio ejercicioSeleccionado) {
        this.ejercicioSeleccionado = ejercicioSeleccionado;
    }

    public Ejercicio getNuevoEjercicio() {
        return nuevoEjercicio;
    }

    public void setNuevoEjercicio(Ejercicio nuevoEjercicio) {
        this.nuevoEjercicio = nuevoEjercicio;
    }

    public List<GrupoMuscular> getGrupoMuscularValues() {
        return List.of(GrupoMuscular.values());
    }

    public void agregarEjercicio() {
        if (nuevoEjercicio.getNombre().isEmpty() || nuevoEjercicio.getDescripcion().isEmpty() || nuevoEjercicio.getGrupoMuscular()== GrupoMuscular.NULL) {
            return;
        }
        ejercicioService.crear(nuevoEjercicio);
        cargarEjercicios();
        nuevoEjercicio = new Ejercicio("", "", "", GrupoMuscular.NULL);
    }

    public void eliminarEjercicio(Ejercicio ejercicio) {
        ejercicioService.eliminar(ejercicio.getId());
        cargarEjercicios();
    }

    public void actualizarEjercicio(Ejercicio ejercicioEditado) {
        ejercicioService.actualizar(ejercicioEditado);
        cargarEjercicios();
    }

    public void prepararNuevoEjercicio() {
        nuevoEjercicio = new Ejercicio("", "", "", GrupoMuscular.NULL);
    }

    public void anadirFavorito(Ejercicio ejercicio) {
        Integer clienteId = getCurrentClienteId();
        if (clienteId == null) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debes iniciar sesión para añadir favoritos"));
            return;
        }
        logger.info("Adding favorite: clienteId=" + clienteId + ", ejercicioId=" + ejercicio.getId());
        ejercicioService.anadirFavorito(clienteId, ejercicio.getId());
        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Ejercicio añadido a favoritos"));
    }

    // Add method to get favorited exercises
    public List<Ejercicio> getEjerciciosFavoritos() {
        Integer clienteId = getCurrentClienteId();
        if (clienteId == null) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debes iniciar sesión para ver favoritos"));
            return List.of(); // Return empty list if not authenticated
        }
        return ejercicioService.obtenerFavoritos(clienteId);
    }

    private Cliente getOrCreateClienteActual() {
        try {
            String username = securityContext.getCallerPrincipal().getName();
            Cliente cliente = clienteDAOJPA.buscaPorNombreUsuario(username);

            if (cliente == null) {
                logger.warning("No cliente found for username: " + username + ", creating new one.");

                cliente = new Cliente();
                cliente.setNombre(username);
                cliente.setDni("00000000-" + username.charAt(0)); // algo temporal
                cliente.setSocio(false);
                cliente.setContrasena("temp"); // solo por cumplir con validaciones
                clienteDAOJPA.crea(cliente); // método típico de persistencia

                logger.info("Cliente creado automáticamente para username: " + username);
            }

            return cliente;
        } catch (Exception e) {
            logger.severe("Error en getOrCreateClienteActual(): " + e.getMessage());
            return null;
        }
    }

    public Integer getCurrentClienteId() {
        try {
            var principal = securityContext.getCallerPrincipal();
            if (principal == null) {
                logger.warning("No authenticated user: principal is null");
                return null;
            }

            String username = principal.getName(); // This will be "antonio", "garcilla", etc.
            logger.info("Authenticated username: " + username);

            Cliente cliente = getOrCreateClienteActual();
            if (cliente == null) {
                logger.info("Cliente no encontrado para username: " + username);
            }

            return cliente.getId();
        } catch (Exception e) {
            logger.severe("Error retrieving clienteId: " + e.getMessage());
            return null;
        }
    }

    public void quitarFavorito(Ejercicio ejercicio) {
        Integer clienteId = getCurrentClienteId();
        ejercicioService.quitarFavorito(clienteId, ejercicio.getId());
    }

}
