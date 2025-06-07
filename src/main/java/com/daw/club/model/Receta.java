package com.daw.club.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.io.Serializable;

@Entity
public class Receta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented ID
    private int id;

    private String nombre;
    private String ingredientes;
    private String pasos;
    private String valorNutricional;
    private int calorias;
    private int proteina;
    private int grasa;
    private int carbohidratos;
    private int fibra;
    private String imagen;

    // Constructor por defecto
    public Receta() {}

    // Constructor completo
    public Receta(String nombre, String ingredientes, String pasos, String valorNutricional,
                  int calorias, int proteina, int grasa, int carbohidratos, int fibra, String imagen) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.valorNutricional = valorNutricional;
        this.calorias = calorias;
        this.proteina = proteina;
        this.grasa = grasa;
        this.carbohidratos = carbohidratos;
        this.fibra = fibra;
        this.imagen = imagen;
    }

    // Getters y setters
    public long getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getIngredientes() { return ingredientes; }
    public void setIngredientes(String ingredientes) { this.ingredientes = ingredientes; }
    public String getPasos() { return pasos; }
    public void setPasos(String pasos) { this.pasos = pasos; }
    public String getValorNutricional() { return valorNutricional; }
    public void setValorNutricional(String valorNutricional) { this.valorNutricional = valorNutricional; }
    public int getCalorias() { return calorias; }
    public void setCalorias(int calorias) { this.calorias = calorias; }
    public int getProteina() { return proteina; }
    public void setProteina(int proteina) { this.proteina = proteina; }
    public int getGrasa() { return grasa; }
    public void setGrasa(int grasa) { this.grasa = grasa; }
    public int getCarbohidratos() { return carbohidratos; }
    public void setCarbohidratos(int carbohidratos) { this.carbohidratos = carbohidratos; }
    public int getFibra() { return fibra; }
    public void setFibra(int fibra) { this.fibra = fibra; }
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    @Override
    public String toString() {
        return "Receta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ingredientes='" + ingredientes + '\'' +
                ", pasos='" + pasos + '\'' +
                ", valorNutricional='" + valorNutricional + '\'' +
                ", calorias=" + calorias +
                ", proteina=" + proteina +
                ", grasa=" + grasa +
                ", carbohidratos=" + carbohidratos +
                ", fibra=" + fibra +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}