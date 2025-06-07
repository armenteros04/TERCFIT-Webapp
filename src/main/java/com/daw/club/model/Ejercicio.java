package com.daw.club.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ejercicio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Genera un ID automático para la entidad
    private Long id;

    private String nombre;
    private String descripcion;
    private String videoUrl;

    @Enumerated(EnumType.STRING)
    private GrupoMuscular grupoMuscular;

    // Constructor por defecto
    public Ejercicio() {}

    public Ejercicio(String nombre, String descripcion, String videoUrl, GrupoMuscular grupoMuscular) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.videoUrl = videoUrl;
        this.grupoMuscular = grupoMuscular;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public GrupoMuscular getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(GrupoMuscular grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    @ManyToMany(mappedBy = "favoritos")
    private Set<Cliente> clientesFavoritos = new HashSet<>();

    public Set<Cliente> getClientesFavoritos() {
        return clientesFavoritos;
    }

    public void setClientesFavoritos(Set<Cliente> clientesFavoritos) {
        this.clientesFavoritos = clientesFavoritos;
    }
}
