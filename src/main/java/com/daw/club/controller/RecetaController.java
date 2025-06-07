package com.daw.club.controller;

import com.daw.club.model.Ejercicio;
import com.daw.club.model.service.RecetaService;
import com.daw.club.model.Receta;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Named("recetaController")
@ViewScoped
public class RecetaController implements Serializable {

    @Inject
    private RecetaService recetaService;

    private List<Receta> recetas;
    private Receta recetaSeleccionada;
    private Receta nuevaReceta = new Receta();

    @PostConstruct
    public void init() {
        nuevaReceta = new Receta();
        // Set a default value for imagen
        nuevaReceta.setImagen("food.png");
    }

    public void cargarRecetas() {
        recetas = recetaService.obtenerTodos();
    }

    public List<Receta> getRecetas() {
        if (recetas == null) {
            cargarRecetas();
        }
        return recetas;
    }

    public Receta getRecetaSeleccionada() {
        return recetaSeleccionada;
    }

    public void setRecetaSeleccionada(Receta recetaSeleccionada) {
        this.recetaSeleccionada = recetaSeleccionada;
    }

    public Receta getNuevaReceta() {
        return nuevaReceta;
    }

    public void setNuevaReceta(Receta nuevaReceta) {
        this.nuevaReceta = nuevaReceta;
    }

    public void agregarReceta() {
        if (nuevaReceta.getNombre() == null || nuevaReceta.getNombre().isEmpty()) {
            return;
        }
        recetaService.crear(nuevaReceta);
        cargarRecetas();
        nuevaReceta = new Receta(); // Reset for next entry
    }

    public void eliminarReceta(Receta receta) {
        recetaService.eliminar((int) receta.getId());
        cargarRecetas();
    }

    public void actualizarReceta(Receta recetaEditado) {
        recetaService.actualizar(recetaEditado);
        cargarRecetas();
    }

    public void prepararNuevaReceta() {
        nuevaReceta = new Receta();
    }
}