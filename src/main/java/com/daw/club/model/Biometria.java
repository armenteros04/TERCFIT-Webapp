package com.daw.club.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.io.Serializable;

@Entity
public class Biometria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double peso;
    private double altura;
    private int edad;
    private double suenio;
    private double calorias;
    private double agua;

    public Biometria(double peso, double cConsumidas, double aConsumidas, double hSuenio, int edad, double altura) {
        this.peso = peso;
        this.calorias = cConsumidas;
        this.agua = aConsumidas;
        this.suenio = hSuenio;
        this.edad = edad;
        this.altura = altura;
    }

    public Biometria() {

    }

    public double getSuenio() {
        return suenio;
    }

    public void setSuenio(double suenio) {
        this.suenio = suenio;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public double getAgua() {
        return agua;
    }

    public void setAgua(double agua) {
        this.agua = agua;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Long getId() {
        return id;
    }
}
