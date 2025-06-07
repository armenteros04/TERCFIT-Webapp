package com.daw.club.controller;

import com.daw.club.model.Biometria;
import com.daw.club.model.service.BiometriaService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Named("biometriaController")
@ViewScoped
public class BiometriaController implements Serializable {

    @Inject
    private BiometriaService biometriaService;

    private List<Biometria> biometrías;
    private Biometria biometriaSeleccionada;
    private Biometria nuevaBiometria = new Biometria();

    public void cargarBiometrias() {
        biometrías = biometriaService.obtenerTodas();
    }

    public List<Biometria> getBiometrías() {
        if (biometrías == null) {
            cargarBiometrias();
        }
        return biometrías;
    }

    public Biometria getBiometriaSeleccionada() {
        return biometriaSeleccionada;
    }

    public void setBiometriaSeleccionada(Biometria biometriaSeleccionada) {
        this.biometriaSeleccionada = biometriaSeleccionada;
    }

    public Biometria getNuevaBiometria() {
        return nuevaBiometria;
    }

    public void setNuevaBiometria(Biometria nuevaBiometria) {
        this.nuevaBiometria = nuevaBiometria;
    }

    public void agregarBiometria() {
        if (nuevaBiometria.getPeso() <= 0 || nuevaBiometria.getAltura() <= 0) {
            return; // Validación básica, puedes expandirla
        }
        biometriaService.crear(nuevaBiometria);
        cargarBiometrias();
        nuevaBiometria = new Biometria();
    }

    public void eliminarBiometria(Biometria biometria) {
        biometriaService.eliminar(biometria.getId());
        cargarBiometrias();
    }

    public void actualizarBiometria(Biometria biometriaEditada) {
        biometriaService.actualizar(biometriaEditada);
        cargarBiometrias();
    }

    public void prepararNuevaBiometria() {
        nuevaBiometria = new Biometria();
    }
}
