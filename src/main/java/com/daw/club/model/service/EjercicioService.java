package com.daw.club.model.service;

import com.daw.club.model.Ejercicio;
import com.daw.club.model.dao.EjercicioDAOJPA;
import com.daw.club.qualifiers.DAOJpa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class EjercicioService {

    @Inject @DAOJpa
    private EjercicioDAOJPA ejercicioDAO;

    public List<Ejercicio> obtenerTodos() {
        return ejercicioDAO.listarTodos();
    }

    public Ejercicio obtenerPorId(Long id) {
        return ejercicioDAO.buscarPorId(id);
    }

    public void crear(Ejercicio ejercicio) {
        ejercicioDAO.guardar(ejercicio);
    }

    public void actualizar(Ejercicio ejercicio) {
        ejercicioDAO.actualizar(ejercicio);
    }

    public void eliminar(Long id) {
        ejercicioDAO.eliminar(id);
    }

    public void anadirFavorito(Integer clienteId, Long ejercicioId) {
        ejercicioDAO.anadirFavorito(clienteId, ejercicioId);
    }

    public void quitarFavorito(Integer clienteId, Long ejercicioId) {
        ejercicioDAO.quitarFavorito(clienteId, ejercicioId);
    }

    public List<Ejercicio> obtenerFavoritos(Integer clienteId) {
        return ejercicioDAO.listarFavoritos(clienteId);
    }
}