package com.daw.club.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity()       //JPA annotations
//@Table(name="Clientes")
public class Cliente implements Serializable {

    @Id         //JPA annotations
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto-incremental
    private Integer id;

    @Size(min = 4, max = 25, message = "La longitud ${validatedValue} debe estar entre {min} y {max} caracteres")
    private String nombre;
    @Pattern(regexp = "\\d{8}-?[a-zA-Z]", message = "{cliente.dni.formato}")
    //@Dni(message = "{cliente.dni.formato}")  //Custom validator
    private String dni;
    private boolean socio;

    private LocalDate fechaNacimiento;

    private String claveCifrada;

    @NotEmpty(message = "Debes introducir una contraseña")
    private String contrasena;

    public Cliente() {
        id = 0;
        nombre = "";
        socio = false;
        contrasena = "";
    }

    public Cliente(Integer id, String nombre, String dni, boolean socio) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.socio = socio;
        this.contrasena = "";
    }

    public void setContrasena(String contraseña_cifrada) {
        this.contrasena = contraseña_cifrada;
    }

    /**
     * Copy constructor
     */
    public Cliente(Cliente c) {
        this.id = c.id;
        this.nombre = c.nombre;
        this.dni = c.dni;
        this.socio = c.socio;
        this.fechaNacimiento = c.fechaNacimiento;
        this.claveCifrada = c.claveCifrada;
        this.contrasena = c.contrasena;
        this.favoritos = new HashSet<>(c.favoritos);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the socio
     */
    public boolean isSocio() {
        return socio;
    }

    /**
     * @param socio the socio to set
     */
    public void setSocio(boolean socio) {
        this.socio = socio;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id;
        return hash;
    }

    public String getClaveCifrada() {
        return claveCifrada;
    }

    public Cliente setClaveCifrada(String claveCifrada) {
        this.claveCifrada = claveCifrada;
        return this;
    }

    @Override
    public String toString() {
        return "Cliente[ id=" + id + " ]";
    }

    @ManyToMany
    private Set<Ejercicio> favoritos = new HashSet<>();

    public Set<Ejercicio> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Set<Ejercicio> favoritos) {
        this.favoritos = favoritos;
    }

}
